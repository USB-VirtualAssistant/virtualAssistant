package org.fundacionjala.virtualassistant.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WhisperClientTest {

    private WhisperClient whisperClient;

    @Test
    public void convertToText_AudioFileQuestion_TextConverted() {
        long start = System.currentTimeMillis();
        whisperClient = new WhisperClient();
        String actual = whisperClient.convertToText("src/test/resources/Question.wav");
        String expected = " How is it weather today?";
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed: " + timeElapsed);
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf10seconds_TextConverted() {
        long start = System.currentTimeMillis();
        whisperClient = new WhisperClient();
        String actual = whisperClient.convertToText("src/test/resources/10sec.wav");
        String expected = " Last year Tesla shares were on fire when they went over $1,000 per share.";
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed: " + timeElapsed);
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf20seconds_TextConverted() {
        long start = System.currentTimeMillis();
        whisperClient = new WhisperClient();
        String actual = whisperClient.convertToText("src/test/resources/20sec.wav");
        String expected = " Hi there, this is Valentine and if you can see this text, it means you have successfully used the Whisper API from OpenAI to transcribe this audio to text. I would really appreciate it if you could give this video a thumbs up and subscribe to my channel. Your support helps me create more tutorials like this one. Thanks a lot.";
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed: " + timeElapsed);
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf30seconds_TextConverted() {
        long start = System.currentTimeMillis();
        whisperClient = new WhisperClient();
        String actual = whisperClient.convertToText("src/test/resources/30sec.wav");
        String expected = " A golden ring will please most any girl. The long journey home took a year. She saw a cat in the neighbor's house. A pink shell was found on the sandy beach. Small children came to see him. The grass and bushes were wet with dew. The blind man counted his old coins. A severe storm tore down the barn. She called his name many times. When you hear the bell come quickly.";
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start; 
        System.out.println("Time elapsed: " + timeElapsed);
        assertEquals(expected, actual);
    }
}