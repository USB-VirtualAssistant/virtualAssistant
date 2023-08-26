package org.fundacionjala.virtualassistant.redis.controller;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @PostMapping("/add")
    public Audio addAudio(@RequestParam("file") MultipartFile file) {
        return audioService.saveFile(file);
    }

    @GetMapping("/get/{id}")
    public byte[] getAudio(@PathVariable String id) {
        return audioService.findFileById(id);
    }
}
