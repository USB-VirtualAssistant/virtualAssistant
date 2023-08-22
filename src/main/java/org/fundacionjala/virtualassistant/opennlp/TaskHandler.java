package org.fundacionjala.virtualassistant.opennlp;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.springframework.http.ResponseEntity;

public class TaskHandler {

    private final MusicService MUSIC_SERVICE;

    public final static String INFORMATION = "Asking_For_Information";
    public final static String ALBUMS = "Get_Albums";
    public final static String TRACKS = "Get_Tracks";
    public final static String CONTACTS = "Contacts";
    public final static String DEFAULT = "";

    public TaskHandler() {
        this.MUSIC_SERVICE = null;
    }

    public TaskHandler(MusicService musicService) {
        this.MUSIC_SERVICE = musicService;
    }

    public String handleIntents(String text) {
        IntentWrapper intentWrapper = new IntentWrapper(text);
        String result;

        switch (intentWrapper.getIntent()) {
            case INFORMATION:
                result = askInformation(intentWrapper.getEntities());
                break;
            case ALBUMS:
                result = getAlbums();
                break;
            case TRACKS:
                result = getTracks();
                break;
            case CONTACTS:
                result = contact(intentWrapper.getEntities());
                break;
            default:
                result = DEFAULT;
                break;
        }

        return result;
    }

    private String askInformation(String[] entities) {
        return parseEntities(entities);
    }

    private String getAlbums() {
        ResponseEntity<String> response = MUSIC_SERVICE.getUserSavedAlbums();

        return response.getBody();
    }

    private String getTracks() {
        ResponseEntity<String> response = MUSIC_SERVICE.getUserSavedTracks();

        return response.getBody();
    }

    private String contact(String[] entities) {
        return parseEntities(entities);
    }

    private String parseEntities(String[] entities) {
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < entities.length; i++) {
            query.append(entities[i]);

            if (i < entities.length - 1) {
                query.append(", ");
            }
        }
        return query.toString();
    }
}
