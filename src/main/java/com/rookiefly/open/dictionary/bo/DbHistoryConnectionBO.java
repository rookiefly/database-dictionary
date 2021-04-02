package com.rookiefly.open.dictionary.bo;

import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据库历史连接BO
 *
 * @author rookiefly
 */
public class DbHistoryConnectionBO {

    private Dialect dialect;

    private String aliasName;

    private String url;

    private String user;

    private String password;

    private transient String realPassword;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getAliasName() {
        if (StringUtils.isBlank(aliasName)) {
            aliasName = dialect + "-" + url + "-" + user;
        }
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public String getRealPassword() {
        try {
            realPassword = EncryptUtil.decrypt(password);
            return realPassword;
        } catch (Exception e) {
            return password;
        }
    }

    public void setPassword(String password) {
        try {
            this.password = EncryptUtil.encrypt(password);
        } catch (Exception ex) {
            throw new RuntimeException("encrypt error");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DbHistoryConnectionBO)) {
            return false;
        }
        DbHistoryConnectionBO dbHistoryConnection = (DbHistoryConnectionBO) o;
        return getAliasName().equals(dbHistoryConnection.getAliasName());
    }

    @Override
    public int hashCode() {
        return getAliasName().hashCode();
    }
}
