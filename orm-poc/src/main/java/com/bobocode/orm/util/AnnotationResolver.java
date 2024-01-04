package com.bobocode.orm.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author "Maksym Oliinyk"
 */
public abstract class AnnotationResolver {

    public static <T> void validateEntity(Class<T> entityClass) {
        if (Arrays.stream(entityClass.getDeclaredAnnotations())
                  .noneMatch(annotation -> annotation.annotationType()
                                                     .equals(Entity.class))) {
            throw new IllegalArgumentException("Class " + entityClass.getSimpleName() + " is not annotated with @Entity");
        }
    }

    public static <T> Table getTableAnnotation(Class<T> entityClass) {
        return Arrays.stream(entityClass.getDeclaredAnnotations())
                     .filter(annotation -> annotation.annotationType()
                                                     .equals(Table.class))
                     .map(Table.class::cast)
                     .reduce((f, s) -> {
                         throw new IllegalArgumentException("Class " + entityClass.getSimpleName() + " has more than one field annotated with @Table");
                     })
                     .orElseThrow(() -> new IllegalArgumentException("Class " + entityClass.getSimpleName() + " is not annotated with @Table"));
    }

    public static <T> Field getFieldAnnotatedWithId(Class<T> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                     .filter(field -> field.isAnnotationPresent(Id.class))
                     .reduce((f, s) -> {
                         throw new IllegalArgumentException("Class " + entityClass.getSimpleName() + " has more than one field annotated with @Id");
                     })
                     .orElseThrow(() -> new IllegalArgumentException("Class " + entityClass.getSimpleName() + " is not annotated with @Id"));
    }

    public static String resolveColumnName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return field.getName()
                        .toLowerCase();
        } else {
            return StringUtils.isBlank(column.name())
                   ? field.getName()
                          .toLowerCase()
                   : column.name();
        }
    }

}
