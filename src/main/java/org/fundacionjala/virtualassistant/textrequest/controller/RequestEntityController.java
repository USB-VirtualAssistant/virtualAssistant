package org.fundacionjala.virtualassistant.textrequest.controller;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.fundacionjala.virtualassistant.whisper.client.WhisperClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/textRequest")
@AllArgsConstructor
public class RequestEntityController {
    TextRequestService requestEntityService;
    WhisperClient whisperClient;
    RecordingService recordingService;

    @PostMapping
    public ResponseEntity<TextRequestResponse> createTextRequest(@RequestBody TextRequest textRequest) throws TextRequestException {
        TextRequestResponse textRequestResponse = requestEntityService.createTextRequest(textRequest);
        return new ResponseEntity<>(textRequestResponse, CREATED);
    }
}
