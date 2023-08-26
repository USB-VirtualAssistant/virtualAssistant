package org.fundacionjala.virtualassistant.redis.service;

import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.repository.AudioRepository;
import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AudioService {

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final int DELETION_TIME = 3600;

    public Audio saveFile(MultipartFile file) throws FileSaveException {
        try {
            Audio audio = new Audio();
            audio.setId(UUID.randomUUID().toString());
            audio.setAudioFile(file.getBytes());
            Audio savedAudio = audioRepository.save(audio);
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(savedAudio.getId(), savedAudio.getAudioFile(), DELETION_TIME, TimeUnit.SECONDS);
            return savedAudio;
        } catch (IOException e) {
            throw new FileSaveException(FileSaveException.FAILED_MESSAGE);
        }
    }

    public byte[] findFileById(String id) throws RedisDataNotFoundException {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(id);

        if (value instanceof String) {
            return Base64.getDecoder().decode((String) value);
        } else if (value instanceof byte[]) {
            return (byte[]) value;
        } else {
            throw new RedisDataNotFoundException(RedisDataNotFoundException.FAILED_MESSAGE_EXPECTED + id);
        }
    }
}
