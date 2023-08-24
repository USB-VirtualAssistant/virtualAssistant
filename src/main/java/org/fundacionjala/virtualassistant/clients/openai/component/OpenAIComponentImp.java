package org.fundacionjala.virtualassistant.clients.openai.component;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OpenAIComponentImp implements OpenAIComponent {

    private ChatService service;

    public String getResponse(String message) {
        return service.chat(message);
    }
}