package org.fundacionjala.virtualassistant.player.spotify.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String accessToken;

    public RedirectView redirectToSpotifyAuthorization() {
        String spotifyAuthUrl = "https://accounts.spotify.com/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri + "&scope=user-read-private%20user-read-email user-library-read user-follow-read user-read-playback-state";
        return new RedirectView(spotifyAuthUrl);
    }

    public ResponseEntity<String> spotifyCallback(String code) {
        authorizationCode = code;
        accessToken = exchangeAuthorizationCodeForAccessToken(authorizationCode);

        return ResponseEntity.ok("Logged in successfully");
    }

    @Override
    public String exchangeAuthorizationCodeForAccessToken(String authorizationCode) {
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
    public String getUserSavedAlbumsFromSpotify(String accessToken) {
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
    public String getUserSavedTracksFromSpotify(String accessToken) {
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
    public String getUserFollowingArtistsFromSpotify(String accessToken) {
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

    public String extractAlbumsData(String albumsData) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();

        try {
            JsonNode root = objectMapper.readTree(albumsData);
            JsonNode items = root.path("items");

            for (JsonNode item : items) {
                JsonNode albumNode = item.path("album");
                String artistName = albumNode.path("artists").get(0).path("name").asText();
                String albumName = albumNode.path("name").asText();

                result.append("Artist: ").append(artistName).append(", ");
                result.append("Album: ").append(albumName).append("\n");

                JsonNode tracksNode = albumNode.path("tracks");
                for (JsonNode track : tracksNode.path("items")) {
                    String trackName = track.path("name").asText();
                    result.append("  Track: ").append(trackName).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String extractTracksData(String tracksData) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();

        try {
            JsonNode root = objectMapper.readTree(tracksData);
            JsonNode items = root.path("items");

            for (JsonNode item : items) {
                JsonNode trackNode = item.path("track");
                String artistName = trackNode.path("artists").get(0).path("name").asText();
                String albumName = trackNode.path("album").path("name").asText();
                String trackName = trackNode.path("name").asText();

                result.append("Artist: ").append(artistName).append(", ");
                result.append("Album: ").append(albumName).append(", ");
                result.append("Track: ").append(trackName).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String extractFollowingData(String followingData) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();

        try {
            JsonNode root = objectMapper.readTree(followingData);
            JsonNode artists = root.path("artists").path("items");

            for (JsonNode artist : artists) {
                String artistName = artist.path("name").asText();
                result.append("Artist: ").append(artistName).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    @Override
    public String getUserPlayerInformationFromSpotify(String accessToken) {
        return null;
    }

    @Override
    public void playSongOnDevice(String trackUri) {

    }
}
