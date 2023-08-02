package org.fundacionjala.virtualassistant.controllers;

import org.fundacionjala.virtualassistant.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/speech")
public class SpeechController {

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

        speechService.sendRecord(filename);
        String message = String.format("The file %s has been uploaded", filename);

        return ResponseEntity.ok().body(message);
    }
}

