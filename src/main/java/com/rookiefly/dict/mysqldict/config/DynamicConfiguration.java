package com.rookiefly.dict.mysqldict.config;

import com.rookiefly.dict.mysqldict.service.MysqlDictService;
import com.rookiefly.dict.mysqldict.service.impl.MysqlDictServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties(MysqlDataSourceProperties.class)
public class DynamicConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MysqlDataSourceProperties mysqlDataSourceProperties;

    /*@Bean
    public MysqlDictService dynamicConfiguration() {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MysqlDictServiceImpl.class);
        beanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        *//**
         * 设置属性
         *//*
        beanDefinitionBuilder.addPropertyValue("name", "myConfigure");
        beanDefinitionBuilder.addPropertyValue("jdbcTemplate", applicationContext.getBean(JdbcTemplate.class));

        *//**
         * 注册到spring容器中
         *//*
        beanFactory.registerBeanDefinition("mysqlDictService", beanDefinitionBuilder.getBeanDefinition());
        MysqlDictService mysqlDictService = (MysqlDictService) context.getBean("mysqlDictService");
        return mysqlDictService;
    }*/
}