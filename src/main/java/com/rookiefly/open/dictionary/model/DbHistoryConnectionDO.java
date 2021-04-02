package com.rookiefly.open.dictionary.model;

import lombok.Data;

import java.util.Date;

/**
 * 数据库历史连接
 *
 * @author rookiefly
 */
@Data
public class DbHistoryConnectionDO {

    private String dialect;

    private String aliasName;

    private String url;

    private String user;

    private String password;

    private Date createTime;

    private Date updateTime;

    private String remark;
}
