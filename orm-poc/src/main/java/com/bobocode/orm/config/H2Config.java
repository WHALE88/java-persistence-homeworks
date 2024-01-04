package com.bobocode.orm.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author "Maksym Oliinyk"
 */
public abstract class H2Config {

    static String DEFAULT_DB = "bobocode";
    static String DEFAULT_USERNAME = "po-user";
    static String DEFAULT_PASSWORD = "po-user";

    public static DataSource dataSource() {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        final String url = formatH2ImMemoryDbUrl(DEFAULT_DB);
        h2DataSource.setUrl(url);
        h2DataSource.setUser(DEFAULT_USERNAME);
        h2DataSource.setPassword(DEFAULT_PASSWORD);
        return h2DataSource;
    }

    private static String formatH2ImMemoryDbUrl(String databaseName) {
        return """ 
               jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;""".formatted(databaseName);
    }

}