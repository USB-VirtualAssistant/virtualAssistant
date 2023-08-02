package org.fundacionjala.virtualassistant.controllers.mongo;


import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.fundacionjala.virtualassistant.services.mongo.RecordingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ResponseEntity<Recording> getRecordingById(@PathVariable String id) {
        Recording recording = recordingService.getRecording(id);
        if (recording != null) {
            return new ResponseEntity<>(recording, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecording(@PathVariable String id) {
        if (recordingService.deleteRecording(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/chat/{idChat}")
    public ResponseEntity<List<Recording>> getAllRecordingsToUser(@RequestParam() Long idUser, @RequestParam() Long idChat) {
        List<Recording> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Recording> saveRecording(@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat, @RequestParam("file") MultipartFile file) {
        Recording savedRecording = recordingService.saveRecording(idUser, idChat, file);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }
}