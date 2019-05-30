package com.rookiefly.dict.mysqldict.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Multiple DataSource Configurer
 */

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    private Map<Object, Object> targetDataSources;

    /**
     * Set dynamic DataSource to Application Context
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug("Current DataSource is [{}]", DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }

    /**
     * 是否存在当前key的 DataSource
     *
     * @param databaseKey
     * @return 存在返回 true, 不存在返回 false
     */
    public boolean isExistDataSource(String databaseKey) {
        return targetDataSources.containsKey(databaseKey);
    }

    /**
     * 动态增加数据源
     *
     * @param dataSourceConfig 数据源属性
     * @return
     */
    public synchronized boolean addDataSource(MysqlDataSourceProperties dataSourceConfig) {
        try {
            String host = dataSourceConfig.getHost();
            String port = dataSourceConfig.getPort();
            String username = dataSourceConfig.getUsername();
            String password = dataSourceConfig.getPassword();
            String schema = dataSourceConfig.getSchema();
            String driverClassName = "com.mysql.cj.jdbc.Driver";
            String jdbcUrl = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=utf8&useSSL=false", host, port, schema);
            Connection connection = null;
            /**
             * 测试数据库链接
             */
            try {
                Class.forName(driverClassName);
                connection = DriverManager.getConnection(
                        jdbcUrl,
                        username,
                        password);
                logger.debug("Current DataSource Closed is [{}]", connection.isClosed());
            } catch (Exception e) {
                logger.error("test mysql connection error", e);
                return false;
            } finally {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            }

            if (StringUtils.isBlank(schema)) return false;
            if (isExistDataSource(schema)) {
                logger.info("dataSource {} is exist", schema);
                return true;
            }

            /**
             * 添加数据源
             */
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            HikariDataSource hikariDataSource = new HikariDataSource(config);
            targetDataSources.put(schema, hikariDataSource);
            afterPropertiesSet();
            logger.info("dataSource {} has been added", schema);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
