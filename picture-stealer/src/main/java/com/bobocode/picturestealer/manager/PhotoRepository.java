package com.bobocode.picturestealer.manager;

import com.bobocode.picturestealer.entity.PhotoEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;
import java.util.UUID;

/**
 * @author "Maksym Oliinyk"
 */
public interface PhotoRepository
        extends JpaRepository<PhotoEntity, UUID> {

    @Query("select p from PhotoEntity p where p.nasaId = :nasaId")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true"),
                 @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true")})
    Optional<PhotoEntity> findByNasaId(String nasaId);

}
