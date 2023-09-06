package org.fundacionjala.virtualassistant.taskhandler.actions;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;

import java.util.List;

@AllArgsConstructor
public class ChatAction implements TaskAction {

    private ChatService chatService;

    @Override
    public String handleAction(List<IntentEntity> entities) {
        return chatService.chat(entities.get(0).getEntity());
    }
}
