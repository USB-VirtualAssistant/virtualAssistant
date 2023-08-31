package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

public class GetAlbumsAction implements TaskAction {
    private MusicService musicService;

    public GetAlbumsAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(String intent) {
        return musicService.getUserSavedAlbums().getBody();
    }
}
