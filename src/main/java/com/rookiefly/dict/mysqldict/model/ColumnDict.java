package com.rookiefly.dict.mysqldict.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ColumnDict implements Serializable {

    private static final long serialVersionUID = 1L;

    private String columnName;

    private String columnType;

    private String columnDefault;

    private String nullable;

    private String autoIncrement;

    private String columnComment;
}
