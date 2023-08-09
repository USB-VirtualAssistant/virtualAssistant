package org.fundacionjala.virtualassistant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
//@TestPropertySource
class WhisperClientTest {

    private WhisperClient whisperClient;

    @Test
    public void convertToText_AudioFileQuestion_TextConverted() {
        whisperClient = new WhisperClient("src/test/resources/Question.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"How is it weather today?\"";
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf10seconds_TextConverted() {
        whisperClient = new WhisperClient("src/test/resources/10sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"Last year Tesla shares were on fire when they went over $1,000 per share.\"";
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf20seconds_TextConverted() {
        whisperClient = new WhisperClient("src/test/resources/20sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"Hi there, this is Valentine and if you can see this text, it means you have successfully used the Whisper API from OpenAI to transcribe this audio to text. I would really appreciate it if you could give this video a thumbs up and subscribe to my channel. Your support helps me create more tutorials like this one. Thanks a lot.\"";
        assertEquals(expected, actual);
    }

    @Test
    public void convertToText_AudioFileOf30seconds_TextConverted() {
        whisperClient = new WhisperClient("src/test/resources/30sec.wav");
        whisperClient.setUrl("http://localhost:8000");
        whisperClient.setPostEndpoint("/record");
        String actual = whisperClient.convertToText();
        String expected = "\"A golden ring will please most any girl. The long journey home took a year. She saw a cat in the neighbor's house. A pink shell was found on the sandy beach. Small children came to see him. The grass and bushes were wet with dew. The blind man counted his old coins. A severe storm tore down the barn. She called his name many times. When you hear the bell come quickly.\"";
        assertEquals(expected, actual);
    }
}