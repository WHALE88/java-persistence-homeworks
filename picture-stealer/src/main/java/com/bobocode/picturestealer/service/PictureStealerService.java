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
 * Service class for retrieving and processing pictures from an external NASA API.
 * This class uses Spring's WebClient to asynchronously request photo data based on the Martian sol (solar day on Mars).
 * It then processes the incoming data to store photo information in the application's database.
 *
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

    /**
     * Fetches a list of photo data from NASA API for a specific Martian sol.
     * It queries the external service, deserializes the response into {@link PhotosWrapper} objects, and then
     * processes each photo to ensure it's stored in the database.
     * The returned list is sorted based on the NASA ID of the photos.
     *
     * @param sol the Martian sol (solar day on Mars) for which to retrieve photos.
     * @return a Mono emitting a sorted list of {@link PhotoEntity} objects representing the photos from the specified sol.
     */
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

    /**
     * Composes the URI for the NASA API call to fetch photos.
     * This function takes the sol value and creates a URI using the configured host and API key.
     *
     * @param sol the Martian sol for which the URI is to be composed.
     * @return a function that generates a URI for the NASA API call.
     */
    private Function<UriBuilder, URI> composePhotosUri(int sol) {
        return uriBuilder -> uriBuilder.path("/rovers/curiosity/photos")
                                       .queryParam("sol",
                                                   sol)
                                       .queryParam("api_key",
                                                   apiKey)
                                       .build();
    }

}
