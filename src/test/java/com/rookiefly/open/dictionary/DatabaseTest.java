package com.rookiefly.open.dictionary;

import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DefaultDataSource;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.database.IntrospectedColumn;
import com.rookiefly.open.dictionary.database.IntrospectedTable;

import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {

    public static void main(String[] args) {
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf("MYSQL"),
                "jdbc:mysql://localhost:3306/torna",
                "root",
                "123456"
        );
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
