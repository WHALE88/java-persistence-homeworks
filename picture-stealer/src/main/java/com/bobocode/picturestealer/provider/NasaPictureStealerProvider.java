package com.bobocode.picturestealer.provider;

import com.bobocode.picturestealer.dto.response.PhotoResp;
import com.bobocode.picturestealer.service.PictureStealerService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class NasaPictureStealerProvider {

    private final PictureStealerService pictureStealerService;

    @GetMapping(value = "steal/{sol}", produces = "application/json")
    public Mono<ResponseEntity<List<PhotoResp>>> stealImages(@Min(value = 0, message = "the Martian sol should be positive")
                                                             @PathVariable("sol") int sol) {
        return pictureStealerService.getPicturesData(sol)
                                    .map(ResponseEntity::ok);
    }

}
