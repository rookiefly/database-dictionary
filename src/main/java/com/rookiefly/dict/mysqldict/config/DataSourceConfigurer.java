package com.rookiefly.dict.mysqldict.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Multiple DataSource Configurer
 */
@Configuration
public class DataSourceConfigurer {

    /**
     * dict data source.
     *
     * @return DataSource
     */
    @Bean("dictDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.dict")
    public DataSource dictDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * sam data source.
     *
     * @return DataSource
     */
    @Bean("samDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.sam")
    public DataSource samDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * clipboard data source.
     *
     * @return DataSource
     */
    @Bean("clipboardDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.clipboard")
    public DataSource clipboardDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * test data source.
     *
     * @return DataSource
     */
    @Bean("testDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.test")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Dynamic data source.
     *
     * @return DataSource
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(DataSourceKey.dict.name(), dictDataSource());
        dataSourceMap.put(DataSourceKey.sam.name(), samDataSource());
        dataSourceMap.put(DataSourceKey.clipboard.name(), clipboardDataSource());
        dataSourceMap.put(DataSourceKey.test.name(), testDataSource());

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(dictDataSource());
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    /**
     * JdbcTemplate bean.
     * Here to config datasource for JdbcTemplate
     *
     * @return the JdbcTemplate bean
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dynamicDataSource());
        return jdbcTemplate;
    }

    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}

