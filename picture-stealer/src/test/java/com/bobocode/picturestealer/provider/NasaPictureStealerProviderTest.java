package com.bobocode.picturestealer.provider;

import com.bobocode.picturestealer.dto.response.CameraResp;
import com.bobocode.picturestealer.dto.response.PhotoResp;
import com.bobocode.picturestealer.service.PictureStealerService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(NasaPictureStealerProvider.class)
class NasaPictureStealerProviderTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PictureStealerService pictureStealerService;

    @ParameterizedTest(name = "Test {index}: sol={0}")
    @ValueSource(ints = {14, 16})
    void testStealImages(int sol) {
        final CameraResp cameraEntity = mock(CameraResp.class);
        when(cameraEntity.nasaId()).thenReturn("64ac71c9-a782-46ef-b79b-342a71aacaab");

        final PhotoResp photoEntity = mock(PhotoResp.class);
        when(photoEntity.camera()).thenReturn(cameraEntity);
        when(photoEntity.nasaId()).thenReturn("1f3a5a56-be9d-4a82-8ff1-dab80582f1e0");

        Mockito.when(pictureStealerService.getPicturesData(eq(sol))).thenReturn(Mono.just(List.of(photoEntity)));

        final WebTestClient.BodyContentSpec bodyContentSpec = webTestClient.get()
                                                                           .uri("/steal/{sol}", sol)
                                                                           .accept(MediaType.APPLICATION_JSON)
                                                                           .exchange()
                                                                           .expectStatus()
                                                                           .isOk()
                                                                           .expectBody();

        final JsonPathAssertions jsonPathAssertions = bodyContentSpec.jsonPath("$")
                                                                     .isArray()
                                                                     .jsonPath("$")
                                                                     .isArray()
                                                                     .jsonPath("$[0].nasaId");
        jsonPathAssertions.isNotEmpty();
        jsonPathAssertions.isEqualTo("1f3a5a56-be9d-4a82-8ff1-dab80582f1e0");
    }

}