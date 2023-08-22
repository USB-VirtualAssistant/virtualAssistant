package org.fundacionjala.virtualassistant.openai.component;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.AIClientInterface;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OpenAIComponentImp implements OpenAIComponent {

    private AIClientInterface openai;

    public String getResponse(String message) {
        return openai.chat(message);
    }

}
