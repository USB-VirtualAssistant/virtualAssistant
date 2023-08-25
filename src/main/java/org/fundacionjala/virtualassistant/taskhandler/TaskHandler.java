package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

public class TaskHandler {
    public static final Proxy PROXY = new Proxy();

    public String handleIntents(String text) throws IntentException {
        return PROXY.handleIntent(text);
    }

    //TO DO: METHODS
}
