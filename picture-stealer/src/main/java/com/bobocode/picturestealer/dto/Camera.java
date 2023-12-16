package com.bobocode.picturestealer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author "Maksym Oliinyk"
 */
public record Camera(String id, String name, @JsonProperty(value = "full_name") String fullName) {
}
