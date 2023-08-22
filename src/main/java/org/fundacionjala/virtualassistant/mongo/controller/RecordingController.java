package org.fundacionjala.virtualassistant.mongo.controller;

import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.AudioConverter;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recordings")
public class RecordingController {

    RecordingService recordingService;

    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @GetMapping()
    public ResponseEntity<List<RecordingResponse>> getAllRecordings() throws RecordingException {
        List<RecordingResponse> recordings = recordingService.getAllRecordings();
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @GetMapping("/audio/{id}")
    public ResponseEntity<RecordingResponse> getRecordingID(@PathVariable("id") String id) throws RecordingException {
        Optional<RecordingResponse> recording = Optional.ofNullable(recordingService.getRecording(id));
        if (recording.isPresent()){
            return new ResponseEntity<>(recording.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/audio/")
    public ResponseEntity<List<RecordingResponse>> getRecordingsUser(@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat) throws RecordingException {
        List<RecordingResponse> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RecordingResponse> saveRecording(@ModelAttribute RecordingRequest recordingRequest) throws RecordingException {
        RecordingResponse savedRecording = recordingService.saveRecording(recordingRequest);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }

    @GetMapping("/audio/download/{id}")
    public ResponseEntity<InputStreamResource> getRecordingByIdDownload(@NotEmpty @PathVariable("id") String id) throws RecordingException {
        Optional<RecordingResponse> recording = Optional.ofNullable(recordingService.getRecording(id));
        if (recording.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return AudioConverter.convertRecordingToInputStreamResource(recording);
    }
}