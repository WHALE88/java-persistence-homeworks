package com.bobocode.picturestealer.dto.response;

import java.time.OffsetDateTime;

/**
 * @author "Maksym Oliinyk"
 */
public record CameraResp(String id, String nasaId, String name, String fullName, OffsetDateTime createdAt) {
}
