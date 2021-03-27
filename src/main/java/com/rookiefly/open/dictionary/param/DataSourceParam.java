package com.rookiefly.open.dictionary.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DataSourceParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库方言
     * {@link com.rookiefly.open.dictionary.database.Dialect}
     */
    private String dialect;

    /**
     * jdbc url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String host;

    private String port;

    private String schema;
}
