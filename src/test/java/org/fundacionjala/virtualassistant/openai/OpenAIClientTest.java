package org.fundacionjala.virtualassistant.openai;

import org.fundacionjala.virtualassistant.clients.openai.OpenAIClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAIClientTest {

    @Test
    void givenServiceReturnsText_WhenChatIsCalled_ThenResultIsNotNullOrEmpty() {
        OpenAIClient client = new OpenAIClient();
        String result = client.chat("hi how are you");
        assertTrue(isStringNotNullOrEmpty(result), "result should not be null or empty");
    }
    private boolean isStringNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}