package org.fundacionjala.virtualassistant.mongo.controller;

import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;


import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recordings")
public class RecordingController {

    @Autowired
    RecordingService recordingService;

    @GetMapping()
    public ResponseEntity<List<RecordingResponse>> getAllRecordings() {
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
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/audio/{id}")
    public ResponseEntity<List<RecordingResponse>> getRecordingsUser(@PathVariable("id") String audioId,@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat) {
        List<RecordingResponse> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RecordingResponse> saveRecording(@RequestBody RecordingRequest recordingRequest) throws RecordingException {
        RecordingResponse savedRecording = recordingService.saveRecording(recordingRequest);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }
}