package com.bobocode.picturestealer.service;

import com.bobocode.picturestealer.dto.Camera;
import com.bobocode.picturestealer.entity.CameraEntity;
import com.bobocode.picturestealer.manager.CameraRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "Maksym Oliinyk"
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional(propagation = Propagation.REQUIRED)
    public CameraEntity findOrCreateCamera(Camera camera) {
        return cameraRepository.findByNasaId(camera.id())
                               .map(cameraEntity -> {
                                   log.info("Retrieve existing CameraEntity by nasa " +
                                            "identifier {}",
                                            cameraEntity.getNasaId());
                                   return cameraEntity;
                               })
                               .orElseGet(() -> {
                                   log.info("Create new CameraEntity by nasa " +
                                            "identifier {}",
                                            camera.id());
                                   return cameraRepository.save(new CameraEntity(camera.id(),
                                                                                 camera.name(),
                                                                                 camera.fullName()));
                               });
    }

}
