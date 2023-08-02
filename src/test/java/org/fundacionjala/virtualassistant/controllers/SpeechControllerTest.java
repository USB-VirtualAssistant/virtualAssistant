package org.fundacionjala.virtualassistant.controllers;

import org.fundacionjala.virtualassistant.service.SpeechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class SpeechControllerTest {

    private SpeechController speechController;
    private SpeechService speechService;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        speechService = mock(SpeechService.class);
        speechController = new SpeechController(speechService);
        mockFile = mock(MultipartFile.class);
    }

    @Test
    void uploadAudio_NonEmptyFile_OkStatus() {
        String fileName = "audio.mp3";
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn(fileName);

        HttpStatus response = speechController.uploadAudio(mockFile);

        assertEquals(HttpStatus.OK, response);
        verify(speechService).sendRecord(fileName);
    }

    @Test
    void uploadAudio_EmptyFile_BadRequestStatus() {
        when(mockFile.isEmpty()).thenReturn(true);
        HttpStatus response = speechController.uploadAudio(mockFile);

        assertEquals(HttpStatus.BAD_REQUEST, response);
        verify(speechService, never()).sendRecord(anyString());
    }

    @Test
    void uploadAudio_Exception_ExceptionIsReturned() {
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("audio.mp3");
        doThrow(new RuntimeException("Simulated Exception")).when(speechService).sendRecord(anyString());

        assertThrows(RuntimeException.class, () -> speechController.uploadAudio(mockFile));
    }
}
