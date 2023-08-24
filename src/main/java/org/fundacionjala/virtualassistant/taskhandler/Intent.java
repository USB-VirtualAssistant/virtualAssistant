package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.actions.*;

public enum Intent {

    GET_ALBUMS(new GetAlbumsAction(SingletonServices.getMusicService())),
    GET_TRACKS(new GetTracksAction(SingletonServices.getMusicService())),
    GET_FOLLOWING(new GetFollowingAction(SingletonServices.getMusicService())),
    GET_PLAYER(new GetPlayerAction(SingletonServices.getMusicService())),
    PAUSE(new PauseAction(SingletonServices.getMusicService())),
    NEXT(new NextAction(SingletonServices.getMusicService())),
    PREVIOUS(new PreviousAction(SingletonServices.getMusicService())),
    CONTINUE(new ContinueAction(SingletonServices.getMusicService()));

    private TaskAction taskAction;

    Intent(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public TaskAction getTaskAction() {
        return taskAction;
    }
}
