package com.rookiefly.open.dictionary.service.impl;

import com.rookiefly.open.dictionary.bo.DatabaseConnectionHistoryBO;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.mapper.DatabaseConnectionHistoryMapper;
import com.rookiefly.open.dictionary.model.DatabaseConnectionHistoryDO;
import com.rookiefly.open.dictionary.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    @Resource
    private DatabaseConnectionHistoryMapper databaseConnectionHistoryMapper;

    @Override
    public Integer saveDatabaseConnection(DatabaseConnectionHistoryBO dataSourceParam) {
        DatabaseConnectionHistoryDO databaseConnectionHistoryDO = new DatabaseConnectionHistoryDO();
        databaseConnectionHistoryDO.setDialect(dataSourceParam.getDialect().name());
        databaseConnectionHistoryDO.setAliasName(dataSourceParam.getAliasName());
        databaseConnectionHistoryDO.setUrl(dataSourceParam.getUrl());
        databaseConnectionHistoryDO.setUser(dataSourceParam.getUser());
        databaseConnectionHistoryDO.setPassword(dataSourceParam.getPassword());
        Date createTime = new Date();
        databaseConnectionHistoryDO.setCreateTime(createTime);
        databaseConnectionHistoryDO.setUpdateTime(createTime);
        return databaseConnectionHistoryMapper.saveDatabaseConnectionHistory(databaseConnectionHistoryDO);
    }

    @Override
    public Integer deleteDatabaseConnection(String aliasName) {
        return databaseConnectionHistoryMapper.deleteDatabaseConnectionHistory(aliasName);
    }

    @Override
    public DatabaseConnectionHistoryBO queryDatabaseConnection(String aliasName) {
        return null;
    }

    @Override
    public List<DatabaseConnectionHistoryBO> listDatabaseConnection() {
        List<DatabaseConnectionHistoryDO> databaseConnectionHistoryDOList = databaseConnectionHistoryMapper.listDatabaseConnectionHistory();
        return databaseConnectionHistoryDOList.stream().map(this::supplyDatabaseConnectionHistoryBO).collect(Collectors.toList());
    }

    private DatabaseConnectionHistoryBO supplyDatabaseConnectionHistoryBO(DatabaseConnectionHistoryDO databaseConnectionHistoryDO) {
        DatabaseConnectionHistoryBO databaseConnectionHistoryBO = new DatabaseConnectionHistoryBO();
        databaseConnectionHistoryBO.setAliasName(databaseConnectionHistoryDO.getAliasName());
        databaseConnectionHistoryBO.setDialect(Dialect.valueOf(databaseConnectionHistoryDO.getDialect()));
        databaseConnectionHistoryBO.setUrl(databaseConnectionHistoryDO.getUrl());
        databaseConnectionHistoryBO.setUser(databaseConnectionHistoryDO.getUser());
        databaseConnectionHistoryBO.setEncryptPassword(databaseConnectionHistoryDO.getPassword());
        return databaseConnectionHistoryBO;
    }
}
