package com.bobocode.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author "Maksym Oliinyk"
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user_user")
public class User {

    @Id
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String email;

    public User(Long id) {
        this.id = id;
    }

}
