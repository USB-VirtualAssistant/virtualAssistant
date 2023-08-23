package org.fundacionjala.virtualassistant.taskhandler;

public enum Intents {
    GET_ALBUMS("", new GetAlbumsAction());

    private String intent;
    private TaskAction taskAction;

    Intents(String intent, TaskAction taskAction) {
        this.intent = intent;
        this.taskAction = taskAction;
    }

}
