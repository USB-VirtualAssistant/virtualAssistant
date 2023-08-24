package org.fundacionjala.virtualassistant.taskhandler;

public enum Intent {
    GET_ALBUMS(new GetAlbumsAction(SingletonServices.getMusicService()));

    private TaskAction taskAction;

    Intent(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public TaskAction getTaskAction() {
        return taskAction;
    }
}
