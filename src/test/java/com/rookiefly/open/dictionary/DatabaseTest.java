package com.rookiefly.open.dictionary;

import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DefaultDataSource;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.database.IntrospectedColumn;
import com.rookiefly.open.dictionary.database.IntrospectedTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DatabaseTest {

    @Test
    public void testListTableInfo() throws SQLException {
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf("MYSQL"),
                "jdbc:mysql://localhost:3306/torna",
                "root",
                "123456"
        );
        DBMetadataHolder dbMetadataHolder = new DBMetadataHolder(dataSource);

        List<IntrospectedTable> list = dbMetadataHolder.introspectTables(dbMetadataHolder.getDefaultConfig());

        for (IntrospectedTable table : list) {
            System.out.println(table.getName() + "（" + table.getRemarks() + "）:");
            for (IntrospectedColumn column : table.getAllColumns()) {
                System.out.println(column.getName() + " - " +
                        column.getJdbcTypeName() + " - " +
                        column.getJavaProperty() + " - " +
                        column.getFullyQualifiedJavaType().getFullyQualifiedName() + " - " +
                        column.getRemarks());
            }
        }
        Assertions.assertFalse(list.isEmpty());
    }
}
