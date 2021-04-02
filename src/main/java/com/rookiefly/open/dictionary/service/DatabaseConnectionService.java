package com.rookiefly.open.dictionary.service;

import com.rookiefly.open.dictionary.bo.DbHistoryConnectionBO;

import java.util.List;

public interface DatabaseConnectionService {

    Integer saveDatabaseConnection(DbHistoryConnectionBO dataSourceParam);

    Integer deleteDatabaseConnection(String aliasName);

    DbHistoryConnectionBO queryDatabaseConnection(String aliasName);

    List<DbHistoryConnectionBO> listDatabaseConnection();
}
