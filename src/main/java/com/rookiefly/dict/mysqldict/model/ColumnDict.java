package com.rookiefly.dict.mysqldict.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class ColumnDict implements RowMapper<ColumnDict>, Serializable {

    private static final long serialVersionUID = 1L;

    private String columnName;

    private String columnType;

    private String columnDefault;

    private String nullable;

    private String autoIncrement;

    private String columnComment;

    @Override
    public ColumnDict mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColumnDict column = new ColumnDict();
        column.setColumnName(rs.getString("COLUMN_NAME"));
        column.setColumnType(rs.getString("COLUMN_TYPE"));
        column.setColumnComment(rs.getString("COLUMN_COMMENT"));
        column.setNullable(rs.getString("IS_NULLABLE"));
        column.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
        String extra = rs.getString("EXTRA");
        if (extra.equalsIgnoreCase("auto_increment")) {
            column.setAutoIncrement("YES");
        }
        return column;
    }
}
