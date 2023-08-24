package org.fundacionjala.virtualassistant.taskhandler;

public class TaskHandler {
    public static final Proxy PROXY = new Proxy();

    public String handleIntents(String text) {
        return PROXY.handleIntent(text);
    }

    //TO DO: METHODS
}
