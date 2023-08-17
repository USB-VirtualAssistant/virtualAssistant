package org.fundacionjala.virtualassistant.openai;

import org.fundacionjala.virtualassistant.clients.openai.AIClientInterface;
import org.springframework.stereotype.Component;

@Component
public class UserOpenAiImp implements UserOpenAi {

    private AIClientInterface openai;

    public UserOpenAiImp(AIClientInterface openai) {
        this.openai = openai;
    }

    public String getResponse(String message) {
        return openai.chat(message);
    }

}
