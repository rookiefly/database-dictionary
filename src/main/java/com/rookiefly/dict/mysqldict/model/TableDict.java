package com.rookiefly.dict.mysqldict.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
public class TableDict implements RowMapper<TableDict>, Serializable {

    private static final long serialVersionUID = 1L;

    private String tableName;

    private String tableComment;

    private String engine;

    private String tableCollation;

    private List<ColumnDict> columns;

    @Override
    public TableDict mapRow(ResultSet rs, int rowNum) throws SQLException {
        TableDict table = new TableDict();
        table.setTableName(rs.getString("TABLE_NAME"));
        table.setTableComment(rs.getString("TABLE_COMMENT"));
        table.setEngine(rs.getString("ENGINE"));
        table.setTableCollation(rs.getString("TABLE_COLLATION"));
        return table;
    }
}
