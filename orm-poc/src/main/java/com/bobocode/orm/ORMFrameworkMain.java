package com.bobocode.orm;

import com.bobocode.orm.builder.QueryBuilder;
import com.bobocode.orm.config.H2Config;
import com.bobocode.orm.entity.User;
import com.bobocode.orm.manager.EntityManager;
import com.bobocode.orm.manager.EntityManagerImpl;

import javax.sql.DataSource;

/**
 * @author "Maksym Oliinyk"
 */
public class ORMFrameworkMain {

    public static void main(String[] args)
            throws Exception {
        final String query = QueryBuilder.interpretToQuerySelectById(User.class);
        System.out.println(query);

        DataSource dataSource = H2Config.dataSource();
        try (EntityManager entityManager = new EntityManagerImpl(dataSource)) {
            entityManager.init();
            final User user = entityManager.find(User.class,
                                                 1L);
            System.out.println(user);
        }
    }



}
