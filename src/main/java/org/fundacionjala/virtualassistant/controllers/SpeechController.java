package org.fundacionjala.virtualassistant.controllers;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.fundacionjala.virtualassistant.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/speech")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    public SpeechController(SpeechService speechService) {
        this.speechService = speechService;
    }

    public SpeechController() {
    }

    @PostMapping("/record")
    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("The file is empty");
        }
        String filename = file.getOriginalFilename();

        try {
            speechService.sendRecord(filename);
            System.out.println(speechService.getAsrClient().getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body("The file " + filename + " has been uploaded");
    }
}

