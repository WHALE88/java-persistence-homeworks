package com.bobocode.picturestealer.manager;

import com.bobocode.picturestealer.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author "Maksym Oliinyk"
 */
public interface PhotoRepository
        extends JpaRepository<PhotoEntity, UUID> {

    Optional<PhotoEntity> findByNasaId(String nasaId);

}
