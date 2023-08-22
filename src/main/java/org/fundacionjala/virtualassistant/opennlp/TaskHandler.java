package org.fundacionjala.virtualassistant.opennlp;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.springframework.http.ResponseEntity;

public class TaskHandler {

    private final MusicService MUSIC_SERVICE;

    public TaskHandler(MusicService musicService) {
        this.MUSIC_SERVICE = musicService;
    }

    public String handleTask(String text) {
        IntentWrapper intentWrapper = new IntentWrapper(text);
        String result;

        switch (intentWrapper.getIntent()) {
            case "Music_Request":
                result = handleSpotify(intentWrapper.getEntities());
                break;
            case "Setting_reminders":
                result = "google";
                break;
            default:
                System.out.println(intentWrapper.toString());
                result = "chatgpt";
                break;
        }

        return result;
    }

    private String handleSpotify(String[] entities) {
        String entity = (entities.length > 0) ? entities[0] : "";
        String result;

        switch (entity) {
            case "Replay_music":
                result = "replay";
                break;
            case "Reproduce_music":
                result = "reproduce";
                break;
            default:
                result = caseGetAlbums();
                break;
        }
        return result;
    }

    private String caseGetAlbums() {
        ResponseEntity<String> response = MUSIC_SERVICE.getUserSavedAlbums();

        return response.getBody();
    }
}
