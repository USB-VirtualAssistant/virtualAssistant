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

    private String accessToken;

    @Override
    public boolean playSongOnDevice(String trackUri) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"uris\": [\"" + trackUri + "\"]}";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.spotify.com/v1/me/player/play",
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            return response.getStatusCode() == HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
        accessToken = exchangeAuthCodeForAccessToken(code);
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
            return root.path("access_token").asText();
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
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(playerData);

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

            String playbackType = root.path("currently_playing_type").asText();

            result.append("Playback Type: ").append(playbackType).append("\n");

            if (playbackType.equals("track")) {
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
                result.append("Currently playing: ").append(playbackType).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing player data";
        }

        return result.toString();
    }

    @Override
    public void playCurrentSong() {
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
    }

    public int extractCurrentTrackPosition(String playerData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(playerData);

            if (jsonNode.has("progress_ms")) {
                return jsonNode.get("progress_ms").asInt();
            }

            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
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

    public String searchTrackByArtistAndTrack(String artist, String track) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String searchQuery = "https://api.spotify.com/v1/search?q=" + artist + " " + track + "&type=track&limit=1";

        ResponseEntity<String> response = restTemplate.exchange(
                searchQuery,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseData = response.getBody();
            return extractTrackUriFromSearchResponse(responseData);
        }

        return null;
    }

    private String extractTrackUriFromSearchResponse(String responseData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData);

            if (jsonNode.has("tracks") && jsonNode.get("tracks").has("items")) {
                JsonNode items = jsonNode.get("tracks").get("items");
                if (items.isArray() && items.size() > 0) {
                    return items.get(0).get("uri").asText();
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
