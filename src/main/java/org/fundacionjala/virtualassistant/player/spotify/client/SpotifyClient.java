package org.fundacionjala.virtualassistant.player.spotify.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Component
public class SpotifyClient implements MusicClient {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private String authorizationCode;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public RedirectView redirectToSpotifyAuthorization() {
        String spotifyAuthUrl = "https://accounts.spotify.com/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri + "&scope=user-read-private%20user-read-email user-library-read user-follow-read user-read-playback-state app-remote-control user-modify-playback-state";
        return new RedirectView(spotifyAuthUrl);
    }

    public ResponseEntity<String> spotifyCallback(String code) {
        authorizationCode = code;
        accessToken = exchangeAuthCodeForAccessToken(authorizationCode);

        return ResponseEntity.ok("Logged in successfully");
    }

    @Override
    public String exchangeAuthCodeForAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String scopes = "user-library-read";
        String requestBody = "grant_type=authorization_code&code=" + authorizationCode +
                "&redirect_uri=" + redirectUri + "&scope=" + scopes;
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                request,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            String accessToken = root.path("access_token").asText();
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getSavedAlbums(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/albums",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    @Override
    public String getSavedTracks(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/tracks",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    @Override
    public String getFollowed(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/following?type=artist",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    public String extractCurrentTrackUri(String playerData) {
        try {
            // Crear un ObjectMapper para convertir la respuesta JSON a un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(playerData);

            // Verificar si hay información de la canción actualmente en reproducción
            if (jsonNode.has("item")) {
                JsonNode item = jsonNode.get("item");
                if (item.has("uri")) {
                    return item.get("uri").asText();
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPlayerInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/player",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    public String extractPlayerData(String playerData) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();

        try {
            JsonNode root = objectMapper.readTree(playerData);

            // Extract relevant information from the JSON response
            String playbackType = root.path("currently_playing_type").asText();

            result.append("Playback Type: ").append(playbackType).append("\n");

            if (playbackType.equals("track")) {
                // Extract track information
                JsonNode trackNode = root.path("item");
                if (trackNode.isMissingNode()) {
                    throw new Exception("No track is currently playing.");
                }

                String artistName = trackNode.path("artists").get(0).path("name").asText();
                String albumName = trackNode.path("album").path("name").asText();
                String trackName = trackNode.path("name").asText();

                result.append("Artist: ").append(artistName).append("\n");
                result.append("Album: ").append(albumName).append("\n");
                result.append("Track: ").append(trackName).append("\n");
            } else {
                // Handle other playback types
                result.append("Currently playing: ").append(playbackType).append("\n");
                // Add additional handling for other playback types if needed
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing player data";
        }

        return result.toString();
    }

    @Override
    public void playSong() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/player/play",
                HttpMethod.PUT,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            System.out.println("Resuming playback on Spotify.");
        } else {
            System.out.println("Failed to resume playback. Status code: " + response.getStatusCode());
        }
    }

    public int extractCurrentTrackPosition(String playerData) {
        try {
            // Create an ObjectMapper to convert the JSON response to a JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(playerData);

            // Check if there is information about the currently playing track
            if (jsonNode.has("progress_ms")) {
                return jsonNode.get("progress_ms").asInt();
            }

            return 0; // Default position if no progress_ms field is present in the response
        } catch (IOException e) {
            e.printStackTrace();
            return 0; // Default position in case of an exception or incorrect data
        }
    }

    public void pauseSongOnDevice(String trackUri) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"uris\": [\"" + trackUri + "\"]}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/player/pause",
                HttpMethod.PUT,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            System.out.println("Song is now paused on Spotify.");
        } else {
            System.out.println("Failed to pause the song. Status code: " + response.getStatusCode());
        }
    }

    public boolean playNextTrackOnDevice() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/player/next",
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    public boolean playPreviousTrackOnDevice() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/player/previous",
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Override
    public void logout() {
        accessToken = null;
    }
}
