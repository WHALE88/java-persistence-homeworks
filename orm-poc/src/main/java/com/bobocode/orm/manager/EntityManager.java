package com.bobocode.orm.manager;

import java.sql.SQLException;

/**
 * @author "Maksym Oliinyk"
 */
public interface EntityManager
        extends AutoCloseable {

    void init() throws SQLException;

    <T> T find(Class<T> entityClass,
               Object primaryKey);

}