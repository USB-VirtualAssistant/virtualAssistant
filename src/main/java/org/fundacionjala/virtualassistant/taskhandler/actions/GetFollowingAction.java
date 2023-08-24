package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

public class GetFollowingAction implements TaskAction {
    private MusicService musicService;

    public GetFollowingAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction() {
        return musicService.getUserFollowingArtists().getBody();
    }
}
