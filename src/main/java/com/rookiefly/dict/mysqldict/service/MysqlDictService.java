package com.rookiefly.dict.mysqldict.service;

import com.rookiefly.dict.mysqldict.model.ColumnDict;
import com.rookiefly.dict.mysqldict.model.TableDict;

import java.util.List;

public interface MysqlDictService {

    TableDict queryTableDict(String tableName, String tableSchema);

    List<ColumnDict> queryColumnDict(String tableName, String tableSchema);

    List<String> showTables();

    List<TableDict> queryMysqlDictBySchema(String schema);
}
