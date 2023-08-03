package org.fundacionjala.virtualassistant.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;

class WhisperClientTest {

    private WhisperClient whisperClient;

    @Test
    public void convertToText_TextConverted() throws IOException, UnsupportedAudioFileException {
        long start = System.currentTimeMillis();
        whisperClient = new WhisperClient();
        String actual = whisperClient.convertToText("30sec.wav");
        String expected = " A gold ring will please most and a girl.  The long journey home took a year.  She saw a cat in the neighbor's house.  A pink shell was found on the sandy beach.  Small children came to see him.  The grass and bushes were wet with dew.  The blind man counted his old coins.  They severe storms tore down the barn.  She called his name many times.  When you hear the bell, come quickly.";
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start; 
        System.out.println("Time elapsed: " + timeElapsed);
        assertEquals(expected, actual);
    }
}