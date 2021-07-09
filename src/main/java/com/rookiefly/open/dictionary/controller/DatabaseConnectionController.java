package com.rookiefly.open.dictionary.controller;

import com.rookiefly.open.dictionary.bo.DatabaseConnectionHistoryBO;
import com.rookiefly.open.dictionary.common.CommonResponse;
import com.rookiefly.open.dictionary.database.DefaultDataSource;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.exception.BizErrorCodeEnum;
import com.rookiefly.open.dictionary.param.DataSourceParam;
import com.rookiefly.open.dictionary.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author rookiefly
 */
@Slf4j
@RestController
public class DatabaseConnectionController {

    @Resource
    private DatabaseConnectionService databaseConnectionService;

    /**
     * 保存数据库连接
     *
     * @param dataSourceParam
     */
    @PostMapping("/connection/test")
    public CommonResponse testDatabaseConnection(@RequestBody @Validated DataSourceParam dataSourceParam) {
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf(dataSourceParam.getDialect()),
                dataSourceParam.getUrl(),
                dataSourceParam.getUser(),
                dataSourceParam.getPassword()
        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return CommonResponse.newSuccessResponse(true);
        } catch (SQLException e) {
            log.error("connection exception", e);
            return CommonResponse.newErrorResponse(BizErrorCodeEnum.DATABASE_CONNECTION_ERROR);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("connection close exception", e);
            }
        }
    }

    /**
     * 保存数据库连接
     *
     * @param dataSourceParam
     */
    @PutMapping("/connection")
    public CommonResponse saveDatabaseConnection(@RequestBody @Validated DataSourceParam dataSourceParam) {
        String aliasName = dataSourceParam.getAliasName();
        String user = dataSourceParam.getUser();
        String url = dataSourceParam.getUrl();
        String dialect = dataSourceParam.getDialect();
        if (StringUtils.isBlank(aliasName)) {
            aliasName = dialect + "-" + url + "@" + user;
        }
        DatabaseConnectionHistoryBO databaseConnectionHistoryBO = new DatabaseConnectionHistoryBO();
        databaseConnectionHistoryBO.setDialect(Dialect.valueOf(dialect));
        databaseConnectionHistoryBO.setUrl(url);
        databaseConnectionHistoryBO.setUser(user);
        databaseConnectionHistoryBO.setPassword(DigestUtils.sha256Hex(dataSourceParam.getPassword()));
        databaseConnectionHistoryBO.setAliasName(aliasName);
        return CommonResponse.newSuccessResponse(databaseConnectionService.saveDatabaseConnection(databaseConnectionHistoryBO));
    }

    /**
     * 删除数据库连接
     *
     * @param aliasName
     * @return
     */
    @DeleteMapping("/connection")
    public CommonResponse deleteDatabaseConnection(@RequestParam String aliasName) {
        return CommonResponse.newSuccessResponse(databaseConnectionService.deleteDatabaseConnection(aliasName));
    }

    /**
     * 查询数据库连接
     *
     * @return
     */
    @GetMapping("/connection")
    @ResponseBody
    public CommonResponse listDatabaseConnection() {
        return CommonResponse.newSuccessResponse(databaseConnectionService.listDatabaseConnection());
    }
}
