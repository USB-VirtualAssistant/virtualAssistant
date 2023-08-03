package org.fundacionjala.virtualassistant.repository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Data
public class WhisperClient implements ASRClient {
    @Value("${asr.whisper.url}")
    private String url;
    @Value("${asr.whisper.post-endpoint}")
    private String postEndpoint;

    private String audioFile;

    public WhisperClient(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public String convertToText() {
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return webClient.post()
                .uri(postEndpoint)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(getBody(audioFile)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private MultiValueMap<String, ?> getBody(String audioFile) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("audio_file", new FileSystemResource(audioFile));
        return body;
    }
}