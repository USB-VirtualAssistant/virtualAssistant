package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

public class TaskHandler {
    private Proxy proxy = new Proxy();

    public String handleIntent(String text) throws IntentException {
        return proxy.handleIntent(text);
    }
}
