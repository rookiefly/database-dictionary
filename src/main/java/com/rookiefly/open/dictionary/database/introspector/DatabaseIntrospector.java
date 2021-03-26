package com.rookiefly.open.dictionary.database.introspector;

import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DatabaseConfig;
import com.rookiefly.open.dictionary.database.FullyQualifiedJavaType;
import com.rookiefly.open.dictionary.database.IntrospectedColumn;
import com.rookiefly.open.dictionary.database.IntrospectedTable;
import com.rookiefly.open.dictionary.utils.JavaBeansUtil;

import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseIntrospector {

    protected static final Map<Integer, JdbcTypeInformation> typeMap;

    static {
        typeMap = new HashMap<>();

        typeMap.put(Types.ARRAY, new JdbcTypeInformation(JDBCType.ARRAY.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.BIGINT, new JdbcTypeInformation(JDBCType.BIGINT.getName(),
                new FullyQualifiedJavaType(Long.class.getName())));
        typeMap.put(Types.BINARY, new JdbcTypeInformation(JDBCType.BINARY.getName(),
                new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BIT, new JdbcTypeInformation(JDBCType.BIT.getName(),
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.BLOB, new JdbcTypeInformation(JDBCType.BLOB.getName(),
                new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BOOLEAN, new JdbcTypeInformation(JDBCType.BOOLEAN.getName(),
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.CHAR, new JdbcTypeInformation(JDBCType.CHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.CLOB, new JdbcTypeInformation(JDBCType.CLOB.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.DATALINK, new JdbcTypeInformation(JDBCType.DATALINK.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DATE, new JdbcTypeInformation(JDBCType.DATE.getName(),
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.DISTINCT, new JdbcTypeInformation(JDBCType.DISTINCT.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DOUBLE, new JdbcTypeInformation(JDBCType.DOUBLE.getName(),
                new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.FLOAT, new JdbcTypeInformation(JDBCType.FLOAT.getName(),
                new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.INTEGER, new JdbcTypeInformation(JDBCType.INTEGER.getName(),
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation(JDBCType.JAVA_OBJECT.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.LONGNVARCHAR, new JdbcTypeInformation(JDBCType.LONGNVARCHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation(
                JDBCType.LONGVARBINARY.getName(),
                new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation(JDBCType.LONGVARCHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCHAR, new JdbcTypeInformation(JDBCType.NCHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCLOB, new JdbcTypeInformation(JDBCType.NCLOB.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NVARCHAR, new JdbcTypeInformation(JDBCType.NVARCHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NULL, new JdbcTypeInformation(JDBCType.NULL.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.OTHER, new JdbcTypeInformation(JDBCType.OTHER.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.REAL, new JdbcTypeInformation(JDBCType.REAL.getName(),
                new FullyQualifiedJavaType(Float.class.getName())));
        typeMap.put(Types.REF, new JdbcTypeInformation(JDBCType.REF.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation(JDBCType.SMALLINT.getName(),
                new FullyQualifiedJavaType(Short.class.getName())));
        typeMap.put(Types.STRUCT, new JdbcTypeInformation(JDBCType.STRUCT.getName(),
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.TIME, new JdbcTypeInformation(JDBCType.TIME.getName(),
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation(JDBCType.TIMESTAMP.getName(),
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation(JDBCType.TINYINT.getName(),
                new FullyQualifiedJavaType(Byte.class.getName())));
        typeMap.put(Types.VARBINARY, new JdbcTypeInformation(JDBCType.VARBINARY.getName(),
                new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.VARCHAR, new JdbcTypeInformation(JDBCType.VARCHAR.getName(),
                new FullyQualifiedJavaType(String.class.getName())));
    }

    protected DBMetadataHolder dbMetadataHolder;
    protected boolean forceBigDecimals;
    protected boolean useCamelCase;

    public DatabaseIntrospector(DBMetadataHolder dbMetadataHolder) {
        this(dbMetadataHolder, false, true);
    }

    public DatabaseIntrospector(DBMetadataHolder dbMetadataHolder, boolean forceBigDecimals, boolean useCamelCase) {
        this.dbMetadataHolder = dbMetadataHolder;
        this.forceBigDecimals = forceBigDecimals;
        this.useCamelCase = useCamelCase;
    }

    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());
        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case Types.DECIMAL:
                case Types.NUMERIC:
                    if (introspectedColumn.getScale() > 0
                            || introspectedColumn.getLength() > 18
                            || forceBigDecimals
                    ) {
                        answer = new FullyQualifiedJavaType(BigDecimal.class
                                .getName());
                    } else if (introspectedColumn.getLength() > 9) {
                        answer = new FullyQualifiedJavaType(Long.class.getName());
                    } else if (introspectedColumn.getLength() > 4) {
                        answer = new FullyQualifiedJavaType(Integer.class.getName());
                    } else {
                        answer = new FullyQualifiedJavaType(Short.class.getName());
                    }
                    break;

                default:
                    answer = null;
                    break;
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }

        return answer;
    }

    public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
        String answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap
                .get(introspectedColumn.getJdbcType());

        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case Types.DECIMAL:
                    answer = JDBCType.DECIMAL.getName();
                    break;
                case Types.NUMERIC:
                    answer = JDBCType.NUMERIC.getName();
                    break;
                default:
                    answer = null;
                    break;
            }
        } else {
            answer = jdbcTypeInformation.getJdbcTypeName();
        }

        return answer;
    }

    public List<String> getCatalogs() throws SQLException {
        ResultSet rs = dbMetadataHolder.getDatabaseMetaData().getCatalogs();
        List<String> catalogs = new ArrayList<>();
        while (rs.next()) {
            catalogs.add(rs.getString(1));
        }
        closeResultSet(rs);
        return catalogs;
    }

    public List<String> getSchemas() throws SQLException {
        ResultSet rs = dbMetadataHolder.getDatabaseMetaData().getSchemas();
        List<String> schemas = new ArrayList<>();
        while (rs.next()) {
            schemas.add(rs.getString(1));
        }
        closeResultSet(rs);
        return schemas;
    }

    public List<String> getTableTypes() throws SQLException {
        ResultSet rs = dbMetadataHolder.getDatabaseMetaData().getTableTypes();
        List<String> tableType = new ArrayList<>();
        while (rs.next()) {
            tableType.add(rs.getString(1));
        }
        closeResultSet(rs);
        return tableType;
    }

    /**
     * 计算主键
     *
     * @param config
     * @param introspectedTable
     */
    protected void calculatePrimaryKey(DatabaseConfig config,
                                       IntrospectedTable introspectedTable) {
        ResultSet rs = null;
        try {
            rs = dbMetadataHolder.getDatabaseMetaData().getPrimaryKeys(
                    config.getCatalog(),
                    config.getSchemaPattern(),
                    introspectedTable.getName());
        } catch (SQLException e) {
            closeResultSet(rs);
            return;
        }

        try {
            Map<Short, String> keyColumns = new TreeMap<>();
            while (rs.next()) {
                //主键列名
                String columnName = rs.getString(MetadataConstants.COLUMN_NAME);
                //主键顺序
                short keySeq = rs.getShort(MetadataConstants.KEY_SEQ);
                keyColumns.put(keySeq, columnName);
            }

            for (String columnName : keyColumns.values()) {
                introspectedTable.addPrimaryKeyColumn(columnName);
            }
        } catch (SQLException e) {
        } finally {
            closeResultSet(rs);
        }
    }

    /**
     * 关闭ResultSet
     *
     * @param rs
     */
    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * 获取表信息
     *
     * @param config
     * @return
     * @throws SQLException
     */
    public List<IntrospectedTable> introspectTables(DatabaseConfig config)
            throws SQLException {
        if (config.hasProcess()) {
            config.getDatabaseProcess().processStart();
        }
        List<IntrospectedTable> introspectedTables = null;
        try {
            DatabaseConfig localConfig = getLocalDatabaseConfig(config);
            Map<IntrospectedTable, List<IntrospectedColumn>> columns = getColumns(localConfig);

            if (columns.isEmpty()) {
                introspectedTables = new ArrayList<>(0);
            } else {
                introspectedTables = calculateIntrospectedTables(localConfig, columns);
                Iterator<IntrospectedTable> iter = introspectedTables.iterator();
                while (iter.hasNext()) {
                    IntrospectedTable introspectedTable = iter.next();
                    //去掉没有字段的表
                    if (!introspectedTable.hasAnyColumns()) {
                        iter.remove();
                    }
                }
            }
        } finally {
            if (config.hasProcess()) {
                config.getDatabaseProcess().processComplete(introspectedTables);
            }
        }
        return introspectedTables;
    }

    /**
     * 根据数据库转换配置
     *
     * @param config
     * @return
     * @throws SQLException
     */
    protected DatabaseConfig getLocalDatabaseConfig(DatabaseConfig config) throws SQLException {
        String localCatalog;
        String localSchema;
        String localTableName;
        if (dbMetadataHolder.getDatabaseMetaData().storesLowerCaseIdentifiers()) {
            localCatalog = config.getCatalog() == null ? null : config.getCatalog()
                    .toLowerCase();
            localSchema = config.getSchemaPattern() == null ? null : config.getSchemaPattern()
                    .toLowerCase();
            localTableName = config.getTableNamePattern() == null ? null : config
                    .getTableNamePattern().toLowerCase();
        } else if (dbMetadataHolder.getDatabaseMetaData().storesUpperCaseIdentifiers()) {
            localCatalog = config.getCatalog() == null ? null : config.getCatalog()
                    .toUpperCase();
            localSchema = config.getSchemaPattern() == null ? null : config.getSchemaPattern()
                    .toUpperCase();
            localTableName = config.getTableNamePattern() == null ? null : config
                    .getTableNamePattern().toUpperCase();
        } else {
            localCatalog = config.getCatalog();
            localSchema = config.getSchemaPattern();
            localTableName = config.getTableNamePattern();
        }
        DatabaseConfig newConfig = new DatabaseConfig(localCatalog, localSchema, localTableName);
        newConfig.setDatabaseProcess(config.getDatabaseProcess());
        return newConfig;
    }

    /**
     * 获取全部的表和字段
     *
     * @param config
     * @return
     * @throws SQLException
     */
    protected Map<IntrospectedTable, List<IntrospectedColumn>> getColumns(DatabaseConfig config) throws SQLException {
        Map<IntrospectedTable, List<IntrospectedColumn>> answer = new HashMap<>();

        ResultSet rs = dbMetadataHolder.getDatabaseMetaData().getColumns(
                config.getCatalog(),
                config.getSchemaPattern(),
                config.getTableNamePattern(),
                null);
        while (rs.next()) {
            IntrospectedColumn column = new IntrospectedColumn();
            column.setJdbcType(rs.getInt(MetadataConstants.DATA_TYPE));
            column.setType(rs.getString(MetadataConstants.TYPE_NAME));
            column.setLength(rs.getInt(MetadataConstants.COLUMN_SIZE));
            column.setName(rs.getString(MetadataConstants.COLUMN_NAME));
            column.setNullable(rs.getInt(MetadataConstants.NULLABLE) == DatabaseMetaData.columnNullable);
            column.setScale(rs.getInt(MetadataConstants.DECIMAL_DIGITS));
            column.setRemarks(rs.getString(MetadataConstants.REMARKS));
            column.setDefaultValue(rs.getString(MetadataConstants.COLUMN_DEF));

            IntrospectedTable table = new IntrospectedTable(
                    rs.getString(MetadataConstants.TABLE_CAT),
                    rs.getString(MetadataConstants.TABLE_SCHEM),
                    rs.getString(MetadataConstants.TABLE_NAME));

            List<IntrospectedColumn> columns = answer.get(table);
            if (columns == null) {
                columns = new ArrayList<>();
                answer.put(table, columns);
                if (config.hasProcess()) {
                    config.getDatabaseProcess().processTable(table);
                }
            }
            if (config.hasProcess()) {
                config.getDatabaseProcess().processColumn(table, column);
            }
            columns.add(column);
        }
        closeResultSet(rs);
        return answer;
    }

    /**
     * 处理表
     *
     * @param config
     * @param columns
     * @return
     * @throws SQLException
     */
    protected List<IntrospectedTable> calculateIntrospectedTables(
            DatabaseConfig config,
            Map<IntrospectedTable, List<IntrospectedColumn>> columns) throws SQLException {
        List<IntrospectedTable> answer = new ArrayList<>();
        //获取表注释信息
        Map<String, String> tableCommentsMap = getTableComments(config);
        Map<String, Map<String, String>> tableColumnCommentsMap = getColumnComments(config);
        for (Map.Entry<IntrospectedTable, List<IntrospectedColumn>> entry : columns
                .entrySet()) {
            IntrospectedTable table = entry.getKey();
            if (tableCommentsMap != null && tableCommentsMap.containsKey(table.getName())) {
                table.setRemarks(tableCommentsMap.get(table.getName()));
            }
            Map<String, String> columnCommentsMap = null;
            if (tableColumnCommentsMap != null && tableColumnCommentsMap.containsKey(table.getName())) {
                columnCommentsMap = tableColumnCommentsMap.get(table.getName());
            }
            for (IntrospectedColumn introspectedColumn : entry.getValue()) {
                FullyQualifiedJavaType fullyQualifiedJavaType = calculateJavaType(introspectedColumn);
                if (fullyQualifiedJavaType != null) {
                    introspectedColumn.setFullyQualifiedJavaType(fullyQualifiedJavaType);
                    introspectedColumn.setJdbcTypeName(calculateJdbcTypeName(introspectedColumn));
                }
                //转换为驼峰形式
                if (useCamelCase) {
                    introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(introspectedColumn.getName(), false));
                } else {
                    introspectedColumn.setJavaProperty(JavaBeansUtil.getValidPropertyName(introspectedColumn.getName()));
                }
                //处理注释
                if (columnCommentsMap != null && columnCommentsMap.containsKey(introspectedColumn.getName())) {
                    introspectedColumn.setRemarks(columnCommentsMap.get(introspectedColumn.getName()));
                }
                table.addColumn(introspectedColumn);
            }
            calculatePrimaryKey(config, table);
            answer.add(table);
        }
        return answer;
    }

    /**
     * 获取表名和注释映射
     *
     * @param config
     * @return
     * @throws SQLException
     */
    protected Map<String, String> getTableComments(DatabaseConfig config) throws SQLException {
        ResultSet rs = dbMetadataHolder.getDatabaseMetaData().getTables(config.getCatalog(), config.getSchemaPattern(), config.getTableNamePattern(), null);
        Map<String, String> answer = new HashMap<>();
        while (rs.next()) {
            answer.put(rs.getString(MetadataConstants.TABLE_NAME), rs.getString(MetadataConstants.REMARKS));
        }
        closeResultSet(rs);
        return answer;
    }

    /**
     * 获取表名和列名-注释映射
     *
     * @param config
     * @return
     * @throws SQLException
     */
    protected Map<String, Map<String, String>> getColumnComments(DatabaseConfig config) throws SQLException {
        return null;
    }

    public static class JdbcTypeInformation {
        private String jdbcTypeName;

        private FullyQualifiedJavaType fullyQualifiedJavaType;

        public JdbcTypeInformation(String jdbcTypeName,
                                   FullyQualifiedJavaType fullyQualifiedJavaType) {
            this.jdbcTypeName = jdbcTypeName;
            this.fullyQualifiedJavaType = fullyQualifiedJavaType;
        }

        public String getJdbcTypeName() {
            return jdbcTypeName;
        }

        public FullyQualifiedJavaType getFullyQualifiedJavaType() {
            return fullyQualifiedJavaType;
        }
    }
}
