package org.fundacionjala.virtualassistant.asrOpenAiIntegration.controller;

import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOperations;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/asrOpenAi")
public class AsrController {
    AsrOperations asrOperations;
    @Autowired
    RedisService redisService;

    public AsrController(AsrOperations asrOperations) {
        this.asrOperations = asrOperations;
    }

    @PostMapping("/{id}")
    public byte[] uploadAudio(@NotEmpty @PathVariable("id") String id) throws RecordingException {
        asrOperations.uploadTemporalAudio(id);
        return redisService.getFromRedis(id);
    }
}
