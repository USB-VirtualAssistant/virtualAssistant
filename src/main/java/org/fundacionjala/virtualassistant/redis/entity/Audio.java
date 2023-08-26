package org.fundacionjala.virtualassistant.redis.entity;

import lombok.Getter;

import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("Audio")
public class Audio {
    private String id;
    @Getter
    private byte[] audioFile;

    public void setAudioFile(byte[] audioFile) {
        this.audioFile = audioFile;
    }

    public void setId(String id) {
        this.id = id;
    }
}
