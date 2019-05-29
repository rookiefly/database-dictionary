package com.rookiefly.dict.mysqldict.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * Multiple DataSource Configurer
 */

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<Object, Object> targetDataSources;

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
        DynamicRoutingDataSource.targetDataSources = targetDataSources;
    }

    /**
     * 是否存在当前key的 DataSource
     *
     * @param databaseKey
     * @return 存在返回 true, 不存在返回 false
     */
    public static boolean isExistDataSource(String databaseKey) {
        return targetDataSources.containsKey(databaseKey);
    }

    /**
     * 动态增加数据源
     *
     * @param dataSourceConfig 数据源属性
     * @return
     */
    public synchronized boolean addDataSource(Map<String, String> dataSourceConfig) {
        try {
            Connection connection = null;
            /**
             * 测试数据库链接
             */
            try {
                Class.forName(dataSourceConfig.get(""));
                connection = DriverManager.getConnection(
                        dataSourceConfig.get(""),
                        dataSourceConfig.get(""),
                        dataSourceConfig.get(""));
                logger.debug("Current DataSource Closed is [{}]", connection.isClosed());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            } finally {
                if (connection != null && !connection.isClosed())
                    connection.close();
            }

            String databaseKey = dataSourceConfig.get("databaseKey");
            if (StringUtils.isBlank(databaseKey)) return false;
            if (DynamicRoutingDataSource.isExistDataSource(databaseKey)) return true;

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/dict?useUnicode=true&characterEncoding=utf8&useSSL=false");
            config.setUsername("root");
            config.setPassword("root");
//            config.addDataSourceProperty("cachePrepStmts", "true");
//            config.addDataSourceProperty("prepStmtCacheSize", "250");
//            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            HikariDataSource hikariDataSource = new HikariDataSource(config);
            DynamicRoutingDataSource.targetDataSources.put(databaseKey, hikariDataSource);
            afterPropertiesSet();
            logger.info("dataSource {} has been added", databaseKey);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
