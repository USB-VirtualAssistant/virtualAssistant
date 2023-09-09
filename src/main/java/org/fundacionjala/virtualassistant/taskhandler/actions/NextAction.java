package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;
import org.springframework.stereotype.Component;

@Component
public class NextAction implements TaskAction {
    private MusicService musicService;

    public NextAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(EntityArgs intentEntities) {
        return musicService.playNextTrack().getBody();
    }
}
