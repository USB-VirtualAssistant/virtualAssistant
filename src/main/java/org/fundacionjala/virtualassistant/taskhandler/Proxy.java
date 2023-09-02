package org.fundacionjala.virtualassistant.taskhandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;
import org.fundacionjala.virtualassistant.user_intetions.client.RasaClient;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class Proxy {
    private RasaClient rasaClient;
    private MusicService musicService;
    private ChatService chatService;

    public String handleIntent(String text) throws IntentException {
        IntentResponse intentResponse = processIntent(text);
        String userIntent = intentResponse.getIntent().getName();
        String intentEntity = intentResponse.getIntentEntities().get(0).getEntity();

        return handleAction(userIntent, intentEntity);
    }

    public String handleAction(String userIntent, String intentEntity) throws IntentException {
        TaskActionManagerFactory taskActionManagerFactory = createTaskActionManagerFactory(userIntent);
        IntentFactory intentFactory = new IntentFactoryImpl();
        TaskActionFactory taskActionFactory = taskActionManagerFactory.getTaskActionFactory(userIntent);

        Intent intent = taskActionManagerFactory.getIntent(userIntent);
        IntentManager intentManager = intentFactory.getSpecific(intent);

        return taskActionFactory
                .createTaskAction(intentManager.processIntent(userIntent))
                .handleAction(intentEntity);
    }

    private IntentResponse processIntent(String text) throws IntentException {
        IntentResponse response = rasaClient.processUserIntentsByMicroService(text).getBody();
        if (Objects.isNull(response)) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
        return response;
    }

    private TaskActionManagerFactory createTaskActionManagerFactory(String userIntent) {
        String intentType = userIntent.split("_")[0];
        return new TaskActionManagerFactoryImpl(intentType, musicService, chatService);
    }
}