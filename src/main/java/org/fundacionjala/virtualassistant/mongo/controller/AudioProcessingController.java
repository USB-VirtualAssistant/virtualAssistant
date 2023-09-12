package org.fundacionjala.virtualassistant.mongo.controller;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOpenAiImplementation;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/audio")
@AllArgsConstructor
public class AudioProcessingController {
    private RecordingService recordingService;
    private AsrOpenAiImplementation asrOpenAiImplementation;
    private AudioService audioService;

    @PostMapping
    public ResponseEntity<?> processAudio(@Valid @ModelAttribute RecordingRequest recordingRequest) {
        try {
            audioService.save(recordingRequest.getAudioFile());
            recordingService.saveRecording(recordingRequest);
            return ResponseEntity.ok(asrOpenAiImplementation.asrOpenAIResponse(recordingRequest.getAudioFile()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
