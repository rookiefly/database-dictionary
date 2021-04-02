package com.rookiefly.open.dictionary.controller;

import com.rookiefly.open.dictionary.bo.DbHistoryConnectionBO;
import com.rookiefly.open.dictionary.common.CommonResponse;
import com.rookiefly.open.dictionary.database.DefaultDataSource;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.exception.BizErrorCodeEnum;
import com.rookiefly.open.dictionary.param.DataSourceParam;
import com.rookiefly.open.dictionary.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
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
            } catch (SQLException ignored) {
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
        DbHistoryConnectionBO dbHistoryConnectionBO = new DbHistoryConnectionBO();
        dbHistoryConnectionBO.setDialect(Dialect.valueOf(dataSourceParam.getDialect()));
        dbHistoryConnectionBO.setUrl(dataSourceParam.getUrl());
        dbHistoryConnectionBO.setUser(dataSourceParam.getUser());
        dbHistoryConnectionBO.setPassword(dataSourceParam.getPassword());
        dbHistoryConnectionBO.setAliasName(dataSourceParam.getAliasName());
        return CommonResponse.newSuccessResponse(databaseConnectionService.saveDatabaseConnection(dbHistoryConnectionBO));
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
