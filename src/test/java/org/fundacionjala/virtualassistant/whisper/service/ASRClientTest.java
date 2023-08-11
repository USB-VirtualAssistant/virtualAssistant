package org.fundacionjala.virtualassistant.whisper.service;

import org.fundacionjala.virtualassistant.whisper.client.WhisperClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;
@WebFluxTest(WhisperClient.class)
public class ASRClientTest {

    @MockBean
    private WebClient.Builder webClientBuilder;
    private WhisperClient whisperClient;
    private static final String URL = "http://localhost:8000";
    private static final String POST_ENDPOINT = "/record";


    @Test
    public void uploadAudio_NonEmptyFile_OkStatus() {
        whisperClient = new WhisperClient();
        WebClient webClient = WebClient.builder()
                .baseUrl(URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
        ResponseSpec responseSpec = sendRequest(webClient, "src/test/resources/Question.wav");
        assertEquals(200, responseSpec.toEntity(String.class).block().getStatusCodeValue());
    }

    @Test
    public void uploadAudio_EmptyFile_BadRequestStatus() {
        WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl(URL)
                .build();
        webTestClient.post()
                .uri(POST_ENDPOINT)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue("nonExistentFileData") 
                .exchange()
                .expectStatus().isBadRequest();
    }

    private ResponseSpec sendRequest(WebClient webClient, String audioFile) {
        return webClient.post()
                .uri(POST_ENDPOINT)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(whisperClient.getBody(audioFile)))
                .retrieve();
    }
}
