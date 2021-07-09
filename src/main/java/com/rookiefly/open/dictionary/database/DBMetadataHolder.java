package com.rookiefly.open.dictionary.database;

import com.rookiefly.open.dictionary.database.introspector.DatabaseIntrospector;
import com.rookiefly.open.dictionary.database.introspector.OracleIntrospector;
import com.rookiefly.open.dictionary.database.introspector.PGIntrospector;
import com.rookiefly.open.dictionary.database.introspector.SqlServerIntrospector;
import com.rookiefly.open.dictionary.exception.BizErrorCodeEnum;
import com.rookiefly.open.dictionary.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 数据库操作
 */
@Slf4j
public class DBMetadataHolder {

    private enum LetterCase {
        UPPER, LOWER, NORMAL
    }

    private DefaultDataSource dataSource;

    private Connection connection;

    private LetterCase letterCase;

    private Dialect dialect;

    private DatabaseIntrospector introspector;

    private DatabaseMetaData databaseMetaData;

    private List<String> catalogs;

    private List<String> schemas;

    public DBMetadataHolder(DefaultDataSource dataSource) {
        this(dataSource, false, true);
    }

    public DBMetadataHolder(DefaultDataSource dataSource, boolean forceBigDecimals, boolean useCamelCase) {
        if (dataSource == null) {
            throw new BizException(BizErrorCodeEnum.REQUEST_ERROR, "Argument dataSource can't be null!");
        }
        this.dataSource = dataSource;
        this.dialect = dataSource.getDialect();
        try {
            initLetterCase();
            this.introspector = getDatabaseIntrospector(forceBigDecimals, useCamelCase);
            this.catalogs = introspector.getCatalogs();
            this.schemas = introspector.getSchemas();
            closeConnection();
        } catch (SQLException e) {
            log.error("new DBMetadataHolder exception", e);
        }
    }

    private void initLetterCase() {
        try {
            databaseMetaData = getConnection().getMetaData();
            if (databaseMetaData.storesLowerCaseIdentifiers()) {
                letterCase = LetterCase.LOWER;
            } else if (databaseMetaData.storesUpperCaseIdentifiers()) {
                letterCase = LetterCase.UPPER;
            } else {
                letterCase = LetterCase.NORMAL;
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    public String convertLetterByCase(String value) {
        if (value == null) {
            return null;
        }
        switch (letterCase) {
            case UPPER:
                return value.toUpperCase();
            case LOWER:
                return value.toLowerCase();
            default:
                return value;
        }
    }

    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            if (databaseMetaData != null) {
                return databaseMetaData;
            } else {
                databaseMetaData = connection.getMetaData();
                return databaseMetaData;
            }
        } else {
            databaseMetaData = getConnection().getMetaData();
            return databaseMetaData;
        }
    }

    public DatabaseIntrospector getIntrospector() {
        return introspector;
    }

    private DatabaseIntrospector getDatabaseIntrospector(boolean forceBigDecimals, boolean useCamelCase) {
        switch (dataSource.getDialect()) {
            case ORACLE:
                return new OracleIntrospector(this, forceBigDecimals, useCamelCase);
            case POSTGRESQL:
                return new PGIntrospector(this, forceBigDecimals, useCamelCase);
            case SQLSERVER:
                return new SqlServerIntrospector(this, forceBigDecimals, useCamelCase);
            case DB2:
            case HSQLDB:
            case MARIADB:
            case MYSQL:
            default:
                return new DatabaseIntrospector(this, forceBigDecimals, useCamelCase);
        }
    }

    public List<String> getCatalogs() {
        return catalogs;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public List<IntrospectedTable> introspectTables(DatabaseConfig config) {
        try {
            getConnection();
            return introspector.introspectTables(config);
        } catch (SQLException e) {
            log.error("introspectTables exception", e);
            return Collections.emptyList();
        } finally {
            closeConnection();
        }
    }

    public static void sortTables(List<IntrospectedTable> tables) {
        if (CollectionUtils.isNotEmpty(tables)) {
            Collections.sort(tables, Comparator.comparing(IntrospectedBase::getName));
        }
    }

    public static void sortColumns(List<IntrospectedColumn> columns) {
        if (CollectionUtils.isNotEmpty(columns)) {
            Collections.sort(columns, Comparator.comparing(IntrospectedColumn::getTableName).thenComparing(IntrospectedBase::getName));
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                databaseMetaData = null;
            } catch (SQLException e) {
                log.error("closeConnection exception", e);
            }
        }
    }

    public boolean testConnection() {
        try {
            if (!getConnection().isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            closeConnection();
        }
        return false;
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        connection = dataSource.getConnection();
        return connection;
    }

    public Dialect getDialect() {
        return dialect;
    }

    /**
     * 获取默认的Config,任何情况下都会返回一个
     *
     * @return
     */
    public DatabaseConfig getDefaultConfig() {
        DatabaseConfig config = null;
        if (catalogs.size() == 1) {
            if (schemas.size() == 1) {
                config = new DatabaseConfig(catalogs.get(0), schemas.get(0));
            } else if (schemas.isEmpty()) {
                config = new DatabaseConfig(catalogs.get(0), null);
            }
        } else if (catalogs.isEmpty()) {
            if (schemas.size() == 1) {
                config = new DatabaseConfig(null, schemas.get(0));
            } else if (schemas.isEmpty()) {
                config = new DatabaseConfig(null, null);
            }
        }
        if (config == null) {
            String dbName = getDbNameByUrl(dataSource.getUrl());
            switch (getDialect()) {
                case DB2:
                case ORACLE:
                    config = new DatabaseConfig(null, dataSource.getUser());
                    break;
                case MYSQL:
                    if (!schemas.isEmpty()) {
                        break;
                    }
                    if (dbName != null) {
                        for (String catalog : catalogs) {
                            if (dbName.equalsIgnoreCase(catalog)) {
                                config = new DatabaseConfig(catalog, null);
                                break;
                            }
                        }
                    }
                    break;
                case SQLSERVER:
                    String sqlserverCatalog = null;
                    if (dbName != null) {
                        for (String catalog : catalogs) {
                            if (dbName.equalsIgnoreCase(catalog)) {
                                sqlserverCatalog = catalog;
                                break;
                            }
                        }
                        if (sqlserverCatalog != null) {
                            for (String schema : schemas) {
                                if ("dbo".equalsIgnoreCase(schema)) {
                                    config = new DatabaseConfig(sqlserverCatalog, "dbo");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case POSTGRESQL:
                    String postgreCatalog = null;
                    if (dbName != null) {
                        for (String catalog : catalogs) {
                            if (dbName.equalsIgnoreCase(catalog)) {
                                postgreCatalog = catalog;
                                break;
                            }
                        }
                        if (postgreCatalog != null) {
                            for (String schema : schemas) {
                                if ("public".equalsIgnoreCase(schema)) {
                                    config = new DatabaseConfig(postgreCatalog, "public");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        if (config == null) {
            config = new DatabaseConfig(null, null);
        }
        return config;
    }

    /**
     * 通过jdbcUrl获取数据库名
     *
     * @param url
     * @return
     */
    private String getDbNameByUrl(String url) {
        if (url.indexOf('/') > -1) {
            String dbName = url.substring(url.lastIndexOf('/') + 1);
            if (dbName.indexOf('?') > -1) {
                dbName = dbName.substring(0, dbName.indexOf('?'));
            }
            return dbName;
        }
        return null;
    }
}
