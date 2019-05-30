package com.rookiefly.dict.mysqldict.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class MysqlDictConfig {

    @Bean("cfg")
    public freemarker.template.Configuration freemarkerConfiguration() {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_27);
        //cfg.setDirectoryForTemplateLoading(new File("/templates"));
        cfg.setClassLoaderForTemplateLoading(MysqlDictConfig.class.getClassLoader(), "templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        return cfg;
    }
}
