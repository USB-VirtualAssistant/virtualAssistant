package org.fundacionjala.virtualassistant.controllers.mongo;


import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.fundacionjala.virtualassistant.services.mongo.RecordingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recordings")
public class RecordingController {

    @Autowired
    RecordingService recordingService;

    @GetMapping()
    public ResponseEntity<List<Recording>> getAllRecordings() {
        List<Recording> recordings = recordingService.getAllRecordings();
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @GetMapping("/audio/{id}")
    public ResponseEntity<Recording> getRecordingID(@PathVariable("id") String id) {
        Optional<Recording> recording = Optional.ofNullable(recordingService.getRecording(id));
        if (recording.isPresent()){
            return new ResponseEntity<>(recording.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/audio/{id}?userId={idUser}&sessionId={idChat}")
    public ResponseEntity<List<Recording>> getRecordingsUser(@PathVariable("id") String audioId,@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat) {
        List<Recording> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Recording> saveRecording(@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat, @RequestParam("file") MultipartFile file) {
        Recording savedRecording = recordingService.saveRecording(idUser, idChat, file);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }
}