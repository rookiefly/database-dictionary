package com.rookiefly.open.dictionary.bo;

import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.utils.EncryptUtil;
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

    public void setPassword(String password) {
        try {
            this.password = EncryptUtil.encrypt(password);
        } catch (Exception ex) {
            throw new RuntimeException("encrypt error");
        }
    }

    public void setEncryptPassword(String encryptPassword) {
        this.password = encryptPassword;
    }
}
