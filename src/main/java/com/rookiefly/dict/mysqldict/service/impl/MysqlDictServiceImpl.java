package com.rookiefly.dict.mysqldict.service.impl;

import com.rookiefly.dict.mysqldict.model.ColumnDict;
import com.rookiefly.dict.mysqldict.model.TableDict;
import com.rookiefly.dict.mysqldict.service.MysqlDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MysqlDictServiceImpl implements MysqlDictService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TableDict queryTableDict(String tableName, String tableSchema) {
        String sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";
        TableDict table = jdbcTemplate.queryForObject(sql, new TableDict(), new Object[]{tableSchema, tableName});
        return table;
    }

    @Override
    public List<ColumnDict> queryColumnDict(String tableName, String tableSchema) {
        String sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";
        List<ColumnDict> columns = jdbcTemplate.query(sql, new ColumnDict(), new Object[]{tableSchema, tableName});
        return columns;
    }

    @Override
    public List<String> showTables() {
        String sql = "SHOW tables";
        List<String> tables = jdbcTemplate.queryForList(sql, String.class);
        return tables;
    }

    @Override
    public List<TableDict> queryMysqlDictBySchema(String schema) {
        List<TableDict> tableDictList = new ArrayList<>();
        List<String> tables = showTables();
        for (String table : tables) {
            TableDict tableDict = queryTableDict(table, schema);
            tableDict.setColumns(queryColumnDict(table, schema));
            tableDictList.add(tableDict);
        }
        return tableDictList;
    }
}
