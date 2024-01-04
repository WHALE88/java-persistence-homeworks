package com.bobocode.orm.manager;

import com.bobocode.orm.builder.QueryBuilder;
import com.bobocode.orm.util.FileReader;
import lombok.Getter;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.bobocode.orm.util.AnnotationResolver.resolveColumnName;

/**
 * @author "Maksym Oliinyk"
 */
public class EntityManagerImpl
        implements EntityManager {

    @Getter
    private final DataSource dataSource;

    public EntityManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init()
            throws SQLException {
        String createTablesSql = FileReader.readWholeFileFromResources("db/migration/table_initialization.sql");

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
        } catch (SQLException e) {
            throw new SQLException("INIT ERROR",
                                   e);
        }
    }

    @Override
    public <T> T find(Class<T> entityClass,
                      Object primaryKey) {
        final String byIdQuery = QueryBuilder.interpretToQuerySelectById(entityClass);
        try (final Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(byIdQuery)) {
            statement.setObject(1,
                                primaryKey);
            try (ResultSet rs = statement.executeQuery()) {
                return parseEntity(entityClass,
                                   rs);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T parseEntity(Class<T> entityClass,
                                     ResultSet rs)
            throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (rs.next()) {  //find first
            T entity = entityClass.getDeclaredConstructor()
                                  .newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                final String columnName = resolveColumnName(field);
                Object value = rs.getObject(columnName);
                field.set(entity,
                          value);
            }

            return entity;
        }
        return null;
    }

    @Override
    public void close()
            throws Exception {

    }

}
