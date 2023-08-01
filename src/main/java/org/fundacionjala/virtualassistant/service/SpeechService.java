package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.repository.ASRClient;
import org.fundacionjala.virtualassistant.repository.WhisperClient;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class SpeechService {

    private ASRClient asrClient;

    public SpeechService(ASRClient asrClient) {
        this.asrClient = asrClient;
    }

    public SpeechService() {
        try {
            asrClient = new WhisperClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRecord(String pathRecord) throws IOException, UnsupportedAudioFileException {
        Path path = Path.of(pathRecord);
        asrClient.convertToText(path);
    }

    public ASRClient getAsrClient() {
        return asrClient;
    }
}

