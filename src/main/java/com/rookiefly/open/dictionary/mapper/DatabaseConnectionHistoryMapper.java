package com.rookiefly.open.dictionary.mapper;

import com.rookiefly.open.dictionary.model.DatabaseConnectionHistoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DatabaseConnectionHistoryMapper {

    /**
     * 查询所有数据库连接配置
     *
     * @return
     */
    List<DatabaseConnectionHistoryDO> listDatabaseConnectionHistory();

    /**
     * 保存数据库连接配置
     *
     * @return
     */
    Integer saveDatabaseConnectionHistory(DatabaseConnectionHistoryDO databaseConnectionHistoryDO);

    /**
     * 删除数据库连接配置
     * @param aliasName
     * @return
     */
    Integer deleteDatabaseConnectionHistory(String aliasName);
}
