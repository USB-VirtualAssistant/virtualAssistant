package org.fundacionjala.virtualassistant.mongo.controller;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOpenAiImplementation;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/audio")
@AllArgsConstructor
public class AudioProcessingController {
    private RecordingService recordingService;
    private AsrOpenAiImplementation asrOpenAiImplementation;
    private AudioService audioService;

    @PostMapping
    public ResponseEntity<?> processAudio(@Valid @ModelAttribute RecordingRequest recordingRequest)
            throws FileSaveException, RecordingException, ParserException,
            IntentException, TextRequestException, IOException {
        audioService.save(recordingRequest.getAudioFile());
        RecordingResponse recordingResponse = recordingService.saveRecording(recordingRequest);
        return ResponseEntity.ok(asrOpenAiImplementation
                .asrOpenAIResponse(recordingRequest, recordingResponse.getIdRecording()));
    }
}
