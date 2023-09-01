package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;
import org.fundacionjala.virtualassistant.user_intetions.client.RasaClient;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.springframework.stereotype.Component;


@Component
public class Proxy {

    private TaskActionManagerFactory taskActionManagerFactory;
    private IntentFactory intentFactory;
    private RasaClient rasaClient;

    public Proxy(TaskActionManagerFactory taskActionManagerFactory, IntentFactory intentFactory, RasaClient rasaClient) {
        this.taskActionManagerFactory = taskActionManagerFactory;
        this.intentFactory = intentFactory;
        this.rasaClient = rasaClient;
    }

    public String handleIntent(String userIntent) throws IntentException {

        return handleIntent(userIntent, "");
    }

    public String handleIntent(String userIntent, IntentResponse intentEntity) throws IntentException {
        TaskActionFactory taskActionFactory = taskActionManagerFactory.getTaskActionFactory(userIntent);
        Intent intent = taskActionManagerFactory.getIntent(userIntent);
        IntentManager intentManager = intentFactory.getSpecific(intent);

        return taskActionFactory
                .createTaskAction(intentManager.processIntent(userIntent))
                .handleAction(intentEntity.getIntentEntities());
    }

}