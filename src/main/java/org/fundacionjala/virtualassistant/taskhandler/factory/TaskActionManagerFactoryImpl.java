package org.fundacionjala.virtualassistant.taskhandler.factory;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class TaskActionManagerFactoryImpl implements TaskActionManagerFactory {
    private Map<String, Intent> intentMap;
    private MusicService musicService;

    public TaskActionManagerFactoryImpl() {
        intentMap = Map.of(
                "music_play", Intent.SPOTIFY,
                "music_pause", Intent.SPOTIFY,
                "music_next",  Intent.SPOTIFY,
                "music_albums",    Intent.SPOTIFY,
                "music_following", Intent.SPOTIFY,
                "music_player",    Intent.SPOTIFY,
                "music_previous",  Intent.SPOTIFY,
                "music_tracks",    Intent.SPOTIFY
        );
    }

    @Override
    public TaskActionFactory getTaskActionFactory(String type) throws IntentException {
        Intent intent = getIntent(type);
        if (Objects.isNull(intent)) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
        switch (intent) {
            case SPOTIFY:
                return new SpotifyTaskActionFactory(musicService);
            default:
                throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
    }

    @Override
    public Intent getIntent(String type) {
        return intentMap.get(type);
    }
}
