package org.fundacionjala.virtualassistant.openai.component;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.taskhandler.TaskHandler;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestComponentImpl implements RequestComponent {

    private TaskHandler handler;

    public String getResponse(String message) {
        try {
            return handler.handleIntent(message);
        } catch (IntentException e) {
            throw new RuntimeException(e);
        }
    }
}