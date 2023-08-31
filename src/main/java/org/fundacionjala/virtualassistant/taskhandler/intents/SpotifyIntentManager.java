package org.fundacionjala.virtualassistant.taskhandler.intents;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SpotifyIntentManager implements IntentManager {

    private Map<String, SpotifyIntent> spotifyIntentMap;

    public SpotifyIntentManager() {
        spotifyIntentMap = Map.of(
                "music_continue", SpotifyIntent.CONTINUE,
                "music_pause", SpotifyIntent.PAUSE,
                "music_next", SpotifyIntent.NEXT,
                "music_albums", SpotifyIntent.GET_ALBUMS,
                "music_following", SpotifyIntent.GET_FOLLOWING,
                "music_player", SpotifyIntent.GET_PLAYER,
                "music_previous", SpotifyIntent.PREVIOUS,
                "music_tracks", SpotifyIntent.GET_TRACKS,
                "music_play", SpotifyIntent.PLAY
        );
    }

    @Override
    public <T extends Enum<?>> T processIntent(String type) {
        return (T) spotifyIntentMap.get(type);
    }
}
