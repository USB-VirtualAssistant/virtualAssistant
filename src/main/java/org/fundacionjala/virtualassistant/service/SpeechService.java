package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.repository.ASRClient;
import org.fundacionjala.virtualassistant.repository.WhisperClient;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class SpeechService {

    private ASRClient asrClient;

    public SpeechService(ASRClient asrClient) {
        this.asrClient = asrClient;
    }

    public SpeechService() {
        asrClient = new WhisperClient();
    }

    public void sendRecord(String pathRecord) {
        Path path = Path.of(pathRecord);
        try {
            asrClient.convertToText(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ASRClient getAsrClient() {
        return asrClient;
    }
}

