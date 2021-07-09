package com.rookiefly.open.dictionary.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class IntrospectedTable extends IntrospectedBase {

    private String schema;

    private String catalog;

    @JsonIgnore
    protected List<IntrospectedColumn> primaryKeyColumns;

    protected List<IntrospectedColumn> baseColumns;

    public IntrospectedTable() {
        super();
        primaryKeyColumns = new ArrayList<>();
        baseColumns = new ArrayList<>();
    }

    public IntrospectedTable(String catalog, String schema, String name) {
        this();
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
    }

    public IntrospectedColumn getColumn(String columnName) {
        if (columnName == null) {
            return null;
        } else {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getName().equals(
                            columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getName()
                            .equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }
            return null;
        }
    }

    public boolean hasJDBCDateColumns() {
        boolean rc = false;
        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCDateColumn()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    public boolean hasJDBCTimeColumns() {
        boolean rc = false;
        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCTimeColumn()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    public List<IntrospectedColumn> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public boolean hasPrimaryKeyColumns() {
        return !primaryKeyColumns.isEmpty();
    }

    public List<IntrospectedColumn> getBaseColumns() {
        return baseColumns;
    }

    public List<IntrospectedColumn> getAllColumns() {
        return baseColumns;
    }

    public boolean hasBaseColumns() {
        return !baseColumns.isEmpty();
    }

    public boolean hasAnyColumns() {
        return !baseColumns.isEmpty();
    }

    public void addColumn(IntrospectedColumn introspectedColumn) {
        baseColumns.add(introspectedColumn);
        introspectedColumn.setTableName(name);
    }

    public void addPrimaryKeyColumn(String columnName) {
        Iterator<IntrospectedColumn> iter = baseColumns.iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.getName().equals(columnName)) {
                introspectedColumn.setPk(true);
                primaryKeyColumns.add(introspectedColumn);
                break;
            }
        }
    }
}
