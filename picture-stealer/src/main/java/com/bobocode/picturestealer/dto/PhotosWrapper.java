package com.bobocode.picturestealer.dto;

import java.util.Collections;
import java.util.List;

/**
 * @author "Maksym Oliinyk"
 */
public record PhotosWrapper(List<Photo> photos) {

    @Override
    public List<Photo> photos() {
        return Collections.unmodifiableList(photos);
    }

}
