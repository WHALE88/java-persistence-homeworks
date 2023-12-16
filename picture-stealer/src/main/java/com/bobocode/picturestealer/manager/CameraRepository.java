package com.bobocode.picturestealer.manager;

import com.bobocode.picturestealer.entity.CameraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author "Maksym Oliinyk"
 */
public interface CameraRepository
        extends JpaRepository<CameraEntity, UUID> {

    Optional<CameraEntity> findByNasaId(String nasaId);

}
