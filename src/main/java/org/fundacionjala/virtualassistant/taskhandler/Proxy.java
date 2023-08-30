package org.fundacionjala.virtualassistant.taskhandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;

@AllArgsConstructor
@NoArgsConstructor
public class Proxy {

    private TaskActionManagerFactory taskActionManagerFactory;
    private IntentFactory intentFactory;

    public String handleIntent(String userIntent) throws IntentException {
        TaskActionFactory taskActionFactory = taskActionManagerFactory.getTaskActionFactory(userIntent);

        Intent intent = taskActionManagerFactory.getIntent(userIntent);
        IntentManager intentManager = intentFactory.getSpecific(intent);

        return taskActionFactory
                .createTaskAction(intentManager.processIntent(userIntent))
                .handleAction();
    }
}
