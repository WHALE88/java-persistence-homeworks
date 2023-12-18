package com.bobocode.picturestealer.dto.response;

import java.time.OffsetDateTime;

/**
 * @author "Maksym Oliinyk"
 */

public record PhotoResp(String id, String nasaId, CameraResp camera, String imgSrc, OffsetDateTime createdAt) {
}
