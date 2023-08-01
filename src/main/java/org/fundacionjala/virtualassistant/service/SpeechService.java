package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.repository.ASRClient;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class SpeechService {

    private ASRClient asrClient;

    public SpeechService(ASRClient asrClient) {
        this.asrClient = asrClient;
    }

    public String sendRecord(String pathRecord) {
        Path path = Path.of(pathRecord);
        return asrClient.convertToText(path);
    }
}
