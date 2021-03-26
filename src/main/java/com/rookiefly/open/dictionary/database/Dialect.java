package com.rookiefly.open.dictionary.database;

/**
 * 数据库 - 驱动和连接示例
 */
public enum Dialect {
    DB2("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://localhost:50000/SAMPLE"),
    HSQLDB("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:sample"),
    MARIADB("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3306/sample"),
    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/sample"),
    ORACLE("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//localhost:1521/orcl"),
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/sample"),
    SQLSERVER("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://localhost:1433/tempdb");

    private String clazz;

    private String sample;

    private Dialect(String clazz, String sample) {
        this.clazz = clazz;
        this.sample = sample;
    }

    public String getSample() {
        return sample;
    }

    public String getDriverClass() {
        return clazz;
    }
}
