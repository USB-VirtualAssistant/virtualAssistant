package org.fundacionjala.virtualassistant.mongoDB.Controller;


import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.fundacionjala.virtualassistant.mongoDB.service.RecordingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/{id}")
    public ResponseEntity<Recording> getRecordingById(@PathVariable Long id) {
        Recording recording = recordingService.getRecording(id);
        if (recording != null) {
            return new ResponseEntity<>(recording, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecording(@PathVariable Long id) {
        if (recordingService.deleteRecording(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/chat/{idChat}")
    public ResponseEntity<List<Recording>> getAllRecordingsToUser(@RequestParam Long idUser, @RequestParam Long idChat) {
        List<Recording> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Recording> saveRecording(@RequestParam("idUser") Long idUser, @RequestParam("idChat") Long idChat, @RequestParam("file") MultipartFile file) {
        Recording savedRecording = recordingService.saveRecording(idUser, idChat, file);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }
}