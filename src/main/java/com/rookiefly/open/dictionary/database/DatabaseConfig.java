package com.rookiefly.open.dictionary.database;

import lombok.Data;

/**
 * 数据库查询配置
 */
@Data
public class DatabaseConfig {

    private String catalog;

    private String schemaPattern;

    private String tableNamePattern;

    private DatabaseProcess databaseProcess;

    public DatabaseConfig() {
        this(null, null);
    }

    public DatabaseConfig(String catalog, String schemaPattern) {
        this(catalog, schemaPattern, "%");
    }

    public DatabaseConfig(String catalog, String schemaPattern, String tableNamePattern) {
        this.catalog = catalog;
        this.schemaPattern = schemaPattern;
        this.tableNamePattern = tableNamePattern;
    }

    public boolean hasProcess() {
        return databaseProcess != null;
    }
}
