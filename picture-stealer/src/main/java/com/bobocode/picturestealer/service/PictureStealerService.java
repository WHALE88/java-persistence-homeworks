package com.bobocode.picturestealer.service;

import com.bobocode.picturestealer.dto.PhotosWrapper;
import com.bobocode.picturestealer.entity.PhotoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author "Maksym Oliinyk"
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PictureStealerService {

    @Value("${nasa.api-key}")
    private String apiKey;
    @Value("${nasa.host}")
    private String host;

    private final WebClient webClient;
    private final PhotoService photoService;

    public Mono<List<PhotoEntity>> getPicturesData(int sol) {
        return webClient.get()
                        .uri(host, composePhotosUri(sol))
                        .retrieve()
                        .bodyToMono(PhotosWrapper.class)
                        .flatMapIterable(PhotosWrapper::photos)
                        .parallel()
                        .map(photoService::createIfNotExists)
                        .collectSortedList(Comparator.comparing(PhotoEntity::getNasaId));
    }

    private Function<UriBuilder, URI> composePhotosUri(int sol) {
        return uriBuilder -> uriBuilder.path("/rovers/curiosity/photos")
                                       .queryParam("sol",
                                                   sol)
                                       .queryParam("api_key",
                                                   apiKey)
                                       .build();
    }

}
