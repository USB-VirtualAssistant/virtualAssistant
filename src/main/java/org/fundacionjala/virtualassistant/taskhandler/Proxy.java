package org.fundacionjala.virtualassistant.taskhandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;

@AllArgsConstructor
@NoArgsConstructor
public class Proxy {

    private TaskActionFactory<SpotifyIntent> taskActionFactory;
    public String handleIntent(String userIntent) throws IntentException {
        try {
            SpotifyIntent enumSpotifyIntent = SpotifyIntent.valueOf(userIntent);
            return taskActionFactory.createTaskAction(enumSpotifyIntent).handleAction();
        } catch (IllegalArgumentException exception) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND, exception);
        }
    }
}
