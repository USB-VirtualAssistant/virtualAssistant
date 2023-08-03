package org.fundacionjala.virtualassistant.player.spotify.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.ErrorMsg;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.MusicPlayerException;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.TokenExtractionException;
import org.fundacionjala.virtualassistant.player.spotify.utils.ApiMusic;
import org.fundacionjala.virtualassistant.player.spotify.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Component
public class SpotifyClient implements MusicClient {
    private final static String SCOPE_USER = "user-library-read";
    private final static String ACCESS_TOKEN = "access_token";
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private String accessToken;
    private final CustomRequest request;
    private final ObjectMapper objectMapper;
    private final JsonNodeManagement managementJN;

    public SpotifyClient() {
        request = new CustomRequest();
        objectMapper = new ObjectMapper();
        managementJN = new JsonNodeManagement(objectMapper);
    }
    
    @Override
    public boolean playSongOnDevice(String trackUri) throws MusicPlayerException {
        try {
            HttpHeaders headers = getHeader();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requestBody = CustomQuery.uriRequestBody(trackUri);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = request.exchange(
                    entity,
                    ApiMusic.PLAY.url(),
                    HttpMethod.PUT
            );

            return response.getStatusCode() == HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new MusicPlayerException(ErrorMsg.FAILED_REQUEST_API.getMessage(),e);
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public RedirectView redirectToSpotifyAuthorization() {
        String spotifyAuthUrl = CustomQuery.authorizationRequestBody(
                clientId,
                redirectUri);
        return new RedirectView(spotifyAuthUrl);
    }

    public ResponseEntity<String> spotifyCallback(String code) {
        try {
            accessToken = exchangeAuthCodeForAccessToken(code);
        } catch (TokenExtractionException e) {
            return CustomResponse.failedLogin(e);
        }
        return CustomResponse.successLogin();
    }

    public String exchangeAuthCodeForAccessToken(String authorizationCode) throws TokenExtractionException  {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = CustomQuery.accessRequestBody(
                authorizationCode,
                redirectUri,
                SCOPE_USER);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = request.exchange(
                entity,
                ApiMusic.TOKEN.pureUrl(),
                HttpMethod.POST
        );

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path(ACCESS_TOKEN).asText();
        } catch (JsonProcessingException e) {
            throw new TokenExtractionException(ErrorMsg.ACCESS_TOKEN_JSON.getMessage(), e);
        }
    }

    @Override
    public String getSavedAlbums() {
        return exchangeGetRequest(ApiMusic.SAVED_ALBUMS.url());
    }

    @Override
    public String getSavedTracks() {
        return exchangeGetRequest(ApiMusic.SAVED_TRACKS.url());
    }

    @Override
    public String getFollowed() {
        return exchangeGetRequest(ApiMusic.FOLLOWED.url());
    }

    @Override
    public String getPlayerInfo() {
        return exchangeGetRequest(ApiMusic.PLAYER_INFO.url());
    }

    private String exchangeGetRequest(String url){
        return request.exchange(
                getAccessToken(),
                url,
                HttpMethod.GET).getBody();
    }

    private HttpHeaders getHeader(){
        return request.createHeader(getAccessToken());
    }

    @Override
    public boolean playCurrentSong() {
        HttpHeaders headers = getHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus response = request.exchange(
                headers,
                ApiMusic.PLAY.url(),
                HttpMethod.PUT
        ).getStatusCode();
        return response.equals(HttpStatus.OK);
    }
    public boolean playNextTrackOnDevice() {
        HttpStatus response = request.exchange(
                getAccessToken(),
                ApiMusic.PLAY_NEXT_TRACK.url(),
                HttpMethod.POST
        ).getStatusCode();

        return response.equals(HttpStatus.NO_CONTENT);
    }

    public boolean playPreviousTrackOnDevice() {
        HttpStatus response = request.exchange(
                getAccessToken(),
                ApiMusic.PLAY_PREVIOUS_TRACK.url(),
                HttpMethod.POST
        ).getStatusCode();

        return response.equals(HttpStatus.NO_CONTENT);
    }

    public boolean pauseSongOnDevice(String trackUri) {
        HttpHeaders headers = getHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = CustomQuery.uriRequestBody(trackUri);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        HttpStatus response = request.exchange(
                entity,
                ApiMusic.PAUSE.url(),
                HttpMethod.PUT
        ).getStatusCode();
        return response.equals(HttpStatus.OK);
    }

    public String searchTrackByArtistAndTrack(String artist, String track) {
        String searchQuery = CustomQuery.searchRequestBody(artist, track);
        ResponseEntity<String> response = request.exchange(
                getAccessToken(),
                searchQuery,
                HttpMethod.GET
        );

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String responseData = response.getBody();
            return extractTrackUriFromSearchResponse(responseData);
        }
        return null;
    }

    public String extractCurrentTrackUri(String playerData) {
        try {
            return managementJN.extractCurrentTrackUri(playerData);
        } catch (TokenExtractionException e) {
            return e.getMessage();
        }
    }

    public String extractPlayerData(String playerData) {
        try {
            return managementJN.extractPlayerData(playerData);
        } catch (TokenExtractionException e) {
            return e.getMessage();
        }
    }

    @Override
    public void logout() {
        accessToken = null;
    }

    private String extractTrackUriFromSearchResponse(String responseData) {
        try {
            return managementJN.extractTrackUriFromSearchResponse(responseData);
        } catch (TokenExtractionException e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean isNotAuthorized(){
        String token = getAccessToken();
        return token == null || token.trim().isEmpty();
    }
}