package com.bobocode.orm.builder;

import jakarta.persistence.Table;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

import static com.bobocode.orm.util.AnnotationResolver.getFieldAnnotatedWithId;
import static com.bobocode.orm.util.AnnotationResolver.getTableAnnotation;
import static com.bobocode.orm.util.AnnotationResolver.resolveColumnName;
import static com.bobocode.orm.util.AnnotationResolver.validateEntity;

/**
 * @author "Maksym Oliinyk"
 */
public abstract class QueryBuilder {

    @SneakyThrows
    public static <T> String interpretToQuerySelectById(Class<T> entityClass) {
        validateEntity(entityClass);
        final String tableName = resolveTableName(entityClass);
        final String idColumnName = resolveIdColumnName(entityClass);
        return """
           SELECT * FROM %s WHERE %s = ?
           """.formatted(tableName, idColumnName);
    }

    private static <T> String resolveIdColumnName(Class<T> entityClass) {
        final Field idField = getFieldAnnotatedWithId(entityClass);
        return resolveColumnName(idField);
    }

    private static <T> String resolveTableName(Class<T> entityClass) {
        final Table tableAnnotation = getTableAnnotation(entityClass);

        return StringUtils.isBlank(tableAnnotation.name())
               ? entityClass.getSimpleName()
                            .toLowerCase()
               : tableAnnotation.name();
    }

}
