package com.rookiefly.open.dictionary.database;

import lombok.Data;

import java.sql.Types;

@Data
public class IntrospectedColumn extends IntrospectedBase {

    protected int jdbcType;

    protected String jdbcTypeName;

    protected boolean nullable;

    protected boolean pk;

    protected int length;

    protected int scale;

    protected String javaProperty;

    protected FullyQualifiedJavaType fullyQualifiedJavaType;

    protected boolean isColumnNameDelimited;

    private String type;

    protected String defaultValue;

    protected String tableName;

    protected String autoincrement;

    public IntrospectedColumn() {
        super();
    }

    public boolean isBLOBColumn() {
        String typeName = getJdbcTypeName();

        return "BINARY".equals(typeName) || "BLOB".equals(typeName)  //$NON-NLS-2$
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName)  //$NON-NLS-2$
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName)  //$NON-NLS-2$
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName);  //$NON-NLS-2$
    }

    public boolean isStringColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getStringInstance());
    }

    public boolean isJdbcCharacterColumn() {
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB
                || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR
                || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public boolean isJDBCDateColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getDateInstance())
                && "DATE".equalsIgnoreCase(jdbcTypeName);
    }

    public boolean isJDBCTimeColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getDateInstance())
                && "TIME".equalsIgnoreCase(jdbcTypeName);
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    public String getJdbcTypeName() {
        if (jdbcTypeName == null) {
            return "OTHER";
        }
        return jdbcTypeName;
    }
}
