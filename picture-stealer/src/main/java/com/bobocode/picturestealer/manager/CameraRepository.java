package com.bobocode.picturestealer.manager;

import com.bobocode.picturestealer.entity.CameraEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;
import java.util.UUID;

/**
 * @author "Maksym Oliinyk"
 */
public interface CameraRepository
        extends JpaRepository<CameraEntity, UUID> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true"),
                 @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true")})
    Optional<CameraEntity> findByNasaId(String nasaId);

}
