package com.bobocode.picturestealer.provider;

import com.bobocode.picturestealer.entity.PhotoEntity;
import com.bobocode.picturestealer.service.PictureStealerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author "Maksym Oliinyk"
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class NasaPictureStealerProvider {

    private final PictureStealerService pictureStealerService;

    @GetMapping(value = "steal/{sol}", produces = "application/json")
    public Mono<ResponseEntity<List<PhotoEntity>>> stealImages(@PathVariable("sol") int sol) {
        return pictureStealerService.getPicturesData(sol)
                                    .map(ResponseEntity::ok);
    }

}
