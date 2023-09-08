package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;

public class PlayAction implements TaskAction {
    private MusicService musicService;

    public PlayAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(EntityArgs intentEntities) {
        return musicService.playSongByArtistAndTrack(intentEntities.getPrimaryArg().getEntity(),
                intentEntities.getSecondaryArg().getEntity()).getBody();
    }
}
