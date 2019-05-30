package com.rookiefly.dict.mysqldict.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "mysql")
@Setter
@Getter
public class MysqlDataSourceProperties {

    private String host;

    private String port;

    private String username;

    private String password;

    private String schema;

    private List<String> schemas = new ArrayList<>();
}
