package org.fundacionjala.virtualassistant.controllers;

import org.fundacionjala.virtualassistant.service.SpeechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class SpeechControllerTest {

    private SpeechController speechController;
    private SpeechService speechService;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        speechService = mock(SpeechService.class);
        speechController = new SpeechController(speechService);
        multipartFile = mock(MultipartFile.class);
    }

    @Test
    void uploadAudio_NonEmptyFile_OkStatus() {
        String fileName = "audio.mp3";
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        HttpStatus response = speechController.uploadAudio(multipartFile);

        assertEquals(HttpStatus.OK, response);
        verify(speechService).sendRecord(fileName);
    }

    @Test
    void uploadAudio_EmptyFile_BadRequestStatus() {
        when(multipartFile.isEmpty()).thenReturn(true);
        HttpStatus response = speechController.uploadAudio(multipartFile);

        assertEquals(HttpStatus.BAD_REQUEST, response);
        verify(speechService, never()).sendRecord(anyString());
    }
}
