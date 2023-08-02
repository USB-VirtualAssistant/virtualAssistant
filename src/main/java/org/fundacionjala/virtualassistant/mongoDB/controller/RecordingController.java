package org.fundacionjala.virtualassistant.mongoDB.controller;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.fundacionjala.virtualassistant.mongoDB.repository.RecordingRepoImpl;
import org.fundacionjala.virtualassistant.mongoDB.service.RecordingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordingController {
    private RecordingServiceImpl recordingService;

    @Autowired
    public  RecordingController ( RecordingServiceImpl recordingService){
        this.recordingService = recordingService;
    }
    @GetMapping("/audio/{audioId}")
    public Recording getAudioBy(@RequestParam Long audioId){
        return recordingService.getRecording(audioId);
    }
    @GetMapping("/audios/{audioId}")
    public List<Recording> getAudiosByAudioIdAndUserAndSession(
            @RequestParam Long audioId,
            @RequestParam Long userId,
            @RequestParam Long sessionId
    ) {
        return recordingService.getAudiosByAudioIdAndUserAndSession(audioId, userId, sessionId);
    }
}
