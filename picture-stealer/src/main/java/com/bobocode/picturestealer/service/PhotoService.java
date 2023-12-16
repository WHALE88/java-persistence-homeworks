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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PhotoEntity createIfNotExists(Photo photo) {
        return photoRepository.findByNasaId(photo.id())
                              .map(photoEntity1 -> {
                                  log.debug("Retrieve existing PhotoEntity by nasa " +
                                            "identifier {}",
                                            photoEntity1.getNasaId());
                                  return photoEntity1;
                              })
                              .orElseGet(() -> {
                                  log.info("Create new PhotoEntity by nasa " +
                                           "identifier {}",
                                           photo.id());
                                  final CameraEntity cameraEntity = cameraService.findOrCreateCamera(photo.camera());

                                  return photoRepository.save(new PhotoEntity(photo.id(),
                                                                              cameraEntity,
                                                                              photo.imgSrc()));
                              });
    }

}
