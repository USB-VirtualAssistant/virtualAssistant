package org.fundacionjala.virtualassistant.whisper.client;

import lombok.Data;
import org.fundacionjala.virtualassistant.whisper.repository.ASRClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Data
public class WhisperClient implements ASRClient {

    @Value("${asr.whisper.url}")
    private String url;

    @Value("${asr.whisper.post-endpoint}")
    private String postEndpoint;

    @Override
    public String convertToText(MultipartFile audioFile) throws IOException {
        byte[] audioData = audioFile.getBytes();
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
        return webClient.post()
                .uri(postEndpoint)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(getBody(audioData, audioFile.getOriginalFilename())))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public MultiValueMap<String, Object> getBody(byte[] audioData, String filename) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("audio_file", new ByteArrayResource(audioData, filename));
        return body;
    }
}