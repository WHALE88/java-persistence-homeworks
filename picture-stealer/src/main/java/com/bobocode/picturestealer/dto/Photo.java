package com.bobocode.picturestealer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * @author "Maksym Oliinyk"
 */

public record Photo(String id, int sol, Camera camera, @JsonProperty(value = "img_src") String imgSrc, @JsonProperty(value = "earth_date") LocalDate earthDate) {
}
