package org.fundacionjala.virtualassistant.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhisperClientTest {

    private WhisperClient whisperClient;

    @Test
    public void givenAudioFileQuestion_whenConvertingToText_thenTextConverted() {
        whisperClient = new WhisperClient("src/test/resources/Question.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"How is it weather today?\"";
        assertEquals(expected, actual);
    }

    @Test
    public void givenAudigivenAudioFile10Seconds_whenConvertingToText_thenTextConvertedoFile10Seconds_whenConvertingToText_thenTextConverted() {
        whisperClient = new WhisperClient("src/test/resources/10sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"Last year Tesla shares were on fire when they went over $1,000 per share.\"";
        assertEquals(expected, actual);
    }

    @Test
    public void givenAudioFile20Seconds_whenConvertingToText_thenTextConverted() {
        whisperClient = new WhisperClient("src/test/resources/20sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"Hi there, this is Valentine and if you can see this text, it means you have successfully used the Whisper API from OpenAI to transcribe this audio to text. I would really appreciate it if you could give this video a thumbs up and subscribe to my channel. Your support helps me create more tutorials like this one. Thanks a lot.\"";
        assertEquals(expected, actual);
    }

    @Test
    public void givenAudioFile30Seconds_whenConvertingToText_thenTextConverted() {
        whisperClient = new WhisperClient("src/test/resources/30sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"A golden ring will please most any girl. The long journey home took a year. She saw a cat in the neighbor's house. A pink shell was found on the sandy beach. Small children came to see him. The grass and bushes were wet with dew. The blind man counted his old coins. A severe storm tore down the barn. She called his name many times. When you hear the bell come quickly.\"";
        assertEquals(expected, actual);
    }
}