package com.rookiefly.open.dictionary.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Setter
@Getter
public class DataSourceParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库方言
     * {@link com.rookiefly.open.dictionary.database.Dialect}
     */
    @NotBlank(message = "数据库方言不能为空")
    private String dialect;

    /**
     * jdbc url
     */
    @NotBlank(message = "数据库JDBC连接字符串不能为空")
    private String url;

    /**
     * 用户名
     */
    @NotBlank(message = "数据库用户名不能为空")
    private String user;

    /**
     * 密码
     */
    @NotBlank(message = "数据库密码不能为空")
    private String password;

    private String aliasName;
}
