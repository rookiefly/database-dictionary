package com.rookiefly.open.dictionary.bo;

import com.rookiefly.open.dictionary.database.Dialect;
import lombok.Data;

/**
 * 数据库历史连接BO
 *
 * @author rookiefly
 */
@Data
public class DatabaseConnectionHistoryBO {

    private Dialect dialect;

    private String aliasName;

    private String url;

    private String user;

    private String password;
}
