package com.bobocode.picturestealer.entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(exclude = "camera")
@ToString(onlyExplicitlyIncluded = true)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "photo")
public class PhotoEntity {

    @Id
    private String id;

    @Column(name = "nasa_id", unique = true)
    private String nasaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "camera_id")
    private CameraEntity camera;

    @Column(name = "img_src")
    private String imgSrc;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Version
    private long version;

    public PhotoEntity(String nasaId,
                       CameraEntity camera,
                       String imgSrc) {
        id = UUID.randomUUID()
                 .toString();
        this.nasaId = nasaId;
        this.camera = camera;
        this.imgSrc = imgSrc;
    }

}
