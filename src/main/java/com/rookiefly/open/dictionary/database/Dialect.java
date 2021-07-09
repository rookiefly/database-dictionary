package com.rookiefly.open.dictionary.database;

/**
 * 数据库 - 驱动和连接示例
 */
public enum Dialect {
    /**
     * DB2
     */
    DB2("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://localhost:50000/SAMPLE", "jdbc:db2://${host}:${port}/${schema}"),
    /**
     * HSQLDB
     */
    HSQLDB("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:sample", "jdbc:hsqldb:mem:${schema}"),
    /**
     * MARIADB
     */
    MARIADB("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3306/sample", "jdbc:mariadb://${host}:${port}/${schema}?serverTimezone=UTC"),
    /**
     * MYSQL
     */
    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/sample", "jdbc:mysql://${host}:${port}/${schema}?serverTimezone=UTC"),
    /**
     * ORACLE
     */
    ORACLE("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//localhost:1521/orcl", "jdbc:oracle:thin:@//${host}:${port}/${schema}"),
    /**
     * POSTGRESQL
     */
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/sample", "jdbc:postgresql://${host}:${port}/${schema}"),
    /**
     * SQLSERVER
     */
    SQLSERVER("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://localhost:1433/tempdb", "jdbc:jtds:sqlserver://${host}:${port}/${schema}");

    private final String clazz;

    private final String sample;

    private final String template;

    Dialect(String clazz, String sample, String template) {
        this.clazz = clazz;
        this.sample = sample;
        this.template = template;
    }

    public String getSample() {
        return sample;
    }

    public String getDriverClass() {
        return clazz;
    }

    public String getTemplate() {
        return template;
    }
}
