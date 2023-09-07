package org.fundacionjala.virtualassistant.asrOpenAiIntegration.controller;

import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOpenAiImplementation;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOperations;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.BASE64DecodedMultipartFile;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.redis.service.RedisService;
import org.fundacionjala.virtualassistant.whisper.client.WhisperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/asrOpenAi")
public class AsrController {
    AsrOperations asrOperations;
    @Autowired
    RedisService redisService;
    AsrOpenAiImplementation asrOpenAiImplementation;
    WhisperClient whisperClient;

    public AsrController(AsrOperations asrOperations,AsrOpenAiImplementation asrOpenAiImplementation, WhisperClient whisperClient) {
        this.asrOperations = asrOperations;
        this.asrOpenAiImplementation = asrOpenAiImplementation;
        this.whisperClient = whisperClient;
    }

    @GetMapping("/{id}")
    public String uploadAudio(@PathVariable String id) throws RecordingException, IOException {
        asrOperations.uploadTemporalAudio(id);
        byte[] byteArray = redisService.getFromRedis(id);
        MultipartFile multipartFile = new BASE64DecodedMultipartFile(byteArray, "audio.wav");

        return whisperClient.convertToText(multipartFile);
    }
}