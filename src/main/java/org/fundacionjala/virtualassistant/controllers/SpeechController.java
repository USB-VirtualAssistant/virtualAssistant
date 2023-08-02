package org.fundacionjala.virtualassistant.controllers;

import org.fundacionjala.virtualassistant.service.SpeechService;
import org.springframework.http.HttpStatus;
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
    public HttpStatus uploadAudio(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }

        String filename = file.getOriginalFilename();
        speechService.sendRecord(filename);

        return HttpStatus.OK;
    }
}

