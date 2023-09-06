package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;

import java.util.List;

public class GetPlayerAction implements TaskAction {
    private MusicService musicService;

    public GetPlayerAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(List<IntentEntity> entities) {
        return musicService.getUserPlayerInformation().getBody();
    }
}
