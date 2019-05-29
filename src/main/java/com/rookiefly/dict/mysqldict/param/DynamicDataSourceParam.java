package com.rookiefly.dict.mysqldict.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DynamicDataSourceParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String host;

    private String port;

    private String username;

    private String password;

    private String schema;
}
