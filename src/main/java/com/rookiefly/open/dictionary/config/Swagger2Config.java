package com.rookiefly.open.dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    @Bean
    public Docket defaultApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rookiefly.open.dictionary.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据库字典服务接口文档")
                .description("数据库字典服务接口文档")
                .termsOfServiceUrl("https://github.com/rookiefly/database-dictionary")
                .contact(new Contact("rookiefly", "https://github.com/rookiefly/", "rookiefly@163.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

}