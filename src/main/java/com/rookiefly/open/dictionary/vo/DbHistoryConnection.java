package com.rookiefly.open.dictionary.vo;

import lombok.Data;

/**
 * 数据库历史连接VO
 *
 * @author rookiefly
 */
@Data
public class DbHistoryConnection {

    private String dialect;

    private String aliasName;

    private String url;

    private String user;

    private String password;
}
