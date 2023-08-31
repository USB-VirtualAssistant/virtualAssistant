package org.fundacionjala.virtualassistant.taskhandler.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.actions.*;
import org.fundacionjala.virtualassistant.taskhandler.intents.SpotifyIntent;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

@AllArgsConstructor
@NoArgsConstructor
public class SpotifyTaskActionFactory implements TaskActionFactory {
    private MusicService musicService;

    @Override
    public <T extends Enum<?>> TaskAction createTaskAction(T intent) throws IntentException {
        SpotifyIntent spotifyIntent = (SpotifyIntent) intent;
        switch (spotifyIntent) {
            case GET_ALBUMS:
                return new GetAlbumsAction(musicService);
            case GET_TRACKS:
                return new GetTracksAction(musicService);
            case GET_FOLLOWING:
                return new GetFollowingAction(musicService);
            case GET_PLAYER:
                return new GetPlayerAction(musicService);
            case PAUSE:
                return new PauseAction(musicService);
            case NEXT:
                return new NextAction(musicService);
            case PREVIOUS:
                return new PreviousAction(musicService);
            case CONTINUE:
                return new ContinueAction(musicService);
            case PLAY:
                return new PlayAction(musicService);
            default:
                throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
    }

}
