package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

public class Proxy {
    public String handleIntent(String userIntent) throws IntentException {
        try {
            Intent enumIntent = Intent.valueOf(userIntent);
            return enumIntent.getTaskAction().handleAction();
        } catch (IllegalArgumentException exception) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND, exception);
        }
    }
}
