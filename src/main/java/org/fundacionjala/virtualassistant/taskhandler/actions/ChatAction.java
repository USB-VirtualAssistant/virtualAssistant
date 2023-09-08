package org.fundacionjala.virtualassistant.taskhandler.actions;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;

@AllArgsConstructor
public class ChatAction implements TaskAction {

    private ChatService chatService;

    @Override
    public String handleAction(EntityArgs intentEntities) {
        return chatService.chat(intentEntities.getPrimaryArg().getEntity());
    }
}
