package org.fundacionjala.virtualassistant.mongo.services;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] audioBytes;
    private String name;
    private  String originalName;
    private String type;

    CustomMultipartFile(byte[] audioBytes, String name, String originalName, String type) {
        this.audioBytes = audioBytes;
        this.name          = name;
        this.originalName  = originalName;
        this.type          = type;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalName;
    }

    @Override
    public String getContentType() {
        return type;
    }

    @Override
    public boolean isEmpty() {
        return audioBytes == null || audioBytes.length == 0;
    }

    @Override
    public long getSize() {
        return audioBytes.length;
    }

    @Override
    public byte[] getBytes() {
        return audioBytes;
    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(audioBytes);
    }

    @Override
    public void transferTo(@NotNull File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(audioBytes);
        }
    }
}
