package com.bobocode.picturestealer.service;

import com.bobocode.picturestealer.dto.Photo;
import com.bobocode.picturestealer.entity.CameraEntity;
import com.bobocode.picturestealer.entity.PhotoEntity;
import com.bobocode.picturestealer.manager.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "Maksym Oliinyk"
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {

    private final CameraService cameraService;
    private final PhotoRepository photoRepository;

    //todo: add retry mechanism for resolve OptimisticLockException in case of concurrent requests
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PhotoEntity createIfNotExists(Photo photo) {
        log.debug("Retrieve PhotoEntity by nasa identifier {}", photo.id());
        return photoRepository.findByNasaId(photo.id())
                              .orElseGet(() -> createNewPhotoEntity(photo));
    }

    protected PhotoEntity createNewPhotoEntity(Photo photo) {
        log.info("Create new PhotoEntity by nasa identifier {}", photo.id());
        final CameraEntity cameraEntity = cameraService.findOrCreateCamera(photo.camera());

        return photoRepository.save(new PhotoEntity(photo.id(),
                                                    cameraEntity,
                                                    photo.imgSrc()));
    }

}
