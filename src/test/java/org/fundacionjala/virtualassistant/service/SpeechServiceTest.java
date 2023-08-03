package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.repository.ASRClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpeechServiceTest {

    private SpeechService speechService;
    private ASRClient asrClient;
    private static final String PATH_AUDIO = "audio.wav";
    private static final String EXPECTED_TRANSCRIPTION = "Hello World!";

    @BeforeEach
    void setUp() {
        asrClient = mock(ASRClient.class);
        speechService = new SpeechService(asrClient);
    }

    @Test
    public void sendRecord_AudioPath_Transcription() {
        Path audioPath = Path.of(PATH_AUDIO);
        when(asrClient.convertToText(audioPath)).thenReturn(EXPECTED_TRANSCRIPTION);

        String resultTranscription = speechService.sendRecord(PATH_AUDIO);

        assertEquals(EXPECTED_TRANSCRIPTION, resultTranscription);
        verify(asrClient).convertToText(audioPath);
    }
}
