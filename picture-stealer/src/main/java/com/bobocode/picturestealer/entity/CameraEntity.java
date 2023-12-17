package com.bobocode.picturestealer.entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author "Maksym Oliinyk"
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(onlyExplicitlyIncluded = true)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "camera")
public class CameraEntity {

    @Id
    private String id;

    @Column(name = "nasa_id", unique = true)
    private String nasaId;

    @Column
    private String name;
    @Column(name = "full_name")
    private String fullName;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Version
    private long version;

    public CameraEntity(String nasaId,
                        String name,
                        String fullName) {
        this.id = UUID.randomUUID().toString();
        this.nasaId = nasaId;
        this.name = name;
        this.fullName = fullName;
    }

}
