package org.fundacionjala.virtualassistant.service.whisper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fundacionjala.virtualassistant.repository.whisper.WhisperClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

@WebFluxTest(WhisperClient.class)
public class ASRClientTest {

    @MockBean
    private WebClient.Builder webClientBuilder;

    private WhisperClient whisperClient;

    @Test
    public void uploadAudio_NonEmptyFile_OkStatus() {
        whisperClient = new WhisperClient("src/test/resources/Question.wav");
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
        ResponseSpec responseSpec = sendRequest(webClient, whisperClient.getAudioFile());
        assertEquals(200, responseSpec.toEntity(String.class).block().getStatusCodeValue());
    }

    @Test
    public void uploadAudio_EmptyFile_BadRequestStatus() {
        WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8000")
                .build();
        webTestClient.post()
                .uri("/record")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue("nonExistentFileData") 
                .exchange()
                .expectStatus().isBadRequest();
    }

    private ResponseSpec sendRequest(WebClient webClient, String audioFile) {
        return webClient.post()
                .uri("/record")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(whisperClient.getBody(audioFile)))
                .retrieve();
    }
}
