package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;

public class GetAlbumsAction implements TaskAction{
    private MusicService musicService;

    public GetAlbumsAction(MusicService musicService) {
        this.musicService = musicService;
    }
    @Override
    public String handleAction() {
        // TO DO:
        return musicService.toString();
    }
}
