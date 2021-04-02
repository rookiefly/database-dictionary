package com.rookiefly.open.dictionary.service.impl;

import com.rookiefly.open.dictionary.bo.DbHistoryConnectionBO;
import com.rookiefly.open.dictionary.service.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    @Override
    public Integer saveDatabaseConnection(DbHistoryConnectionBO dataSourceParam) {
        return null;
    }

    @Override
    public Integer deleteDatabaseConnection(String aliasName) {
        return null;
    }

    @Override
    public DbHistoryConnectionBO queryDatabaseConnection(String aliasName) {
        return null;
    }

    @Override
    public List<DbHistoryConnectionBO> listDatabaseConnection() {
        return null;
    }
}
