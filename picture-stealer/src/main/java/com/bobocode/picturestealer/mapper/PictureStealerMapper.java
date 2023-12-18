package com.bobocode.picturestealer.mapper;

import com.bobocode.picturestealer.dto.response.CameraResp;
import com.bobocode.picturestealer.dto.response.PhotoResp;
import com.bobocode.picturestealer.entity.CameraEntity;
import com.bobocode.picturestealer.entity.PhotoEntity;
import org.mapstruct.Mapper;

/**
 * @author "Maksym Oliinyk"
 */

@Mapper(componentModel = "spring")
public interface PictureStealerMapper {

    CameraResp toCameraResp(CameraEntity cameraEntity);

    PhotoResp toPhotoResp(PhotoEntity photoEntity);

}
