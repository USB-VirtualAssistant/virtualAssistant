package org.fundacionjala.virtualassistant.repository;

import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface ASRClient {

    void convertToText(Path audioFile) throws IOException, UnsupportedAudioFileException;

    String getText();
}
