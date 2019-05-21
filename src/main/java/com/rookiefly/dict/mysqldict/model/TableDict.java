package com.rookiefly.dict.mysqldict.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TableDict implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableName;

    private String tableComment;

    private List<ColumnDict> columns;
}
