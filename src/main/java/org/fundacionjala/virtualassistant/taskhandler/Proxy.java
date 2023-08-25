package org.fundacionjala.virtualassistant.taskhandler;

public class Proxy {
    public String handleIntent(String userIntent) {
        Intent enumIntent = Intent.valueOf(userIntent);
        return enumIntent.getTaskAction().handleAction();
    }
}
