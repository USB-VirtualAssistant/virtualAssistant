package org.fundacionjala.virtualassistant.clients.openai.exeption;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.OpenAiError.OpenAiErrorDetails;

public class ChatServiceOpenAiHttpException extends RuntimeException {

    public ChatServiceOpenAiHttpException(OpenAiErrorDetails errorDetails, OpenAiHttpException e) {
        super(errorDetails.getMessage(), e);
    }
    
}
