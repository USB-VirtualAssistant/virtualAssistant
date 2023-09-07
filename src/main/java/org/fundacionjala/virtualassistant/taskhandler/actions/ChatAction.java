package org.fundacionjala.virtualassistant.taskhandler.actions;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

@AllArgsConstructor
public class ChatAction implements TaskAction {

    private ChatService chatService;

    @Override
    public String handleAction(String text) {
        return chatService.chat(text);
    }
}
