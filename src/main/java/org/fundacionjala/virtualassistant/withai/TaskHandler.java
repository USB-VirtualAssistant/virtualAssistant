package org.fundacionjala.virtualassistant.withai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.springframework.http.ResponseEntity;

public class TaskHandler {

    public WitAi witAi = new WitAi();
    private final MusicService musicService;

    public TaskHandler(MusicService musicService) {
        this.musicService = musicService;
    }

    public String handleResponse() {
        String responseBody = witAi.processIntent();

        String userIntent = extractUserIntent(responseBody);

        if (userIntent != null && userIntent.equals("wit$get_weather")) {
            ResponseEntity<String> response = musicService.getUserSavedAlbums();
            String musicResponse = response.getBody();

            System.out.println(musicResponse);
            return musicResponse;
        }

        return "";
    }

    private static String extractUserIntent(String jsonString) {
        String userIntent = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode intentsArray = jsonNode.get("intents");

            if (intentsArray.isArray() && intentsArray.size() > 0) {
                JsonNode intentObject = intentsArray.get(0);
                userIntent = intentObject.get("name").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userIntent;
    }
}
