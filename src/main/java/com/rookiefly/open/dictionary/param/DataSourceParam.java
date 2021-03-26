package com.rookiefly.open.dictionary.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DataSourceParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dialect;

    private String url;

    private String username;

    private String password;

    private String host;

    private String port;

    private String schema;
}
