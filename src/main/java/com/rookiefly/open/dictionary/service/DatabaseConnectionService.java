package com.rookiefly.open.dictionary.service;

import com.rookiefly.open.dictionary.bo.DatabaseConnectionHistoryBO;

import java.util.List;

public interface DatabaseConnectionService {

    Integer saveDatabaseConnection(DatabaseConnectionHistoryBO dataSourceParam);

    Integer deleteDatabaseConnection(String aliasName);

    DatabaseConnectionHistoryBO queryDatabaseConnection(String aliasName);

    List<DatabaseConnectionHistoryBO> listDatabaseConnection();
}
