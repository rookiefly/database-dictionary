package com.rookiefly.dict.mysqldict.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * sql脚本初始化
 */
@Configuration
public class JdbcScriptInit {

    @Bean
    @Profile("dev")
    public InitializingBean sqlScriptInitClipboard(@Qualifier("clipboardDataSource") DataSource dataSource) {
        return getInitializingBean(dataSource, Arrays.asList("db/schema-h2.sql"));
    }

    private InitializingBean getInitializingBean(DataSource dataSource, List<String> scriptPathList) {

        List<ClassPathResource> scriptList = new ArrayList<>();
        for (String scriptPath : scriptPathList) {
            scriptList.add(new ClassPathResource(scriptPath));
        }

        ClassPathResource[] resArray = new ClassPathResource[scriptList.size()];
        resArray = scriptList.toArray(resArray);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(true, true, "utf-8", resArray);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        initializer.setEnabled(true);

        return initializer;
    }
}