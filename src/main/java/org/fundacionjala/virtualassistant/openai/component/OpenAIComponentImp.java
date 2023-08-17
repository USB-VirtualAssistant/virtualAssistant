package org.fundacionjala.virtualassistant.openai.component;

import org.fundacionjala.virtualassistant.clients.openai.AIClientInterface;
import org.springframework.stereotype.Component;

@Component
public class OpenAIComponentImp implements OpenAIComponent {

    private AIClientInterface openai;

    public OpenAIComponentImp(AIClientInterface openai) {
        this.openai = openai;
    }

    public String getResponse(String message) {
        return openai.chat(message);
    }

}
