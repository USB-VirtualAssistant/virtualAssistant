package org.fundacionjala.virtualassistant.asrOpenAiIntegration.service;

import org.fundacionjala.virtualassistant.taskhandler.TaskHandler;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.whisper.client.WhisperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AsrOpenAiImplementation {
    WhisperClient whisperClient;
    TaskHandler taskHandler;

    @Autowired
    public AsrOpenAiImplementation(WhisperClient whisperClient, TaskHandler taskHandler) {
        this.whisperClient = whisperClient;
        this.taskHandler = taskHandler;
    }

    public String asrOpenAIResponse(MultipartFile multipartFile) throws IOException, IntentException {
        String request = cleanText(whisperClient.convertToText(multipartFile));
        return taskHandler.handleIntent(request);
    }
    private String cleanText(String text) {
        return text.replaceAll("\"", "");
    }
}
