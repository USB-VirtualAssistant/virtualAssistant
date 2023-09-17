package org.fundacionjala.virtualassistant.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.*;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    final Long ID_USER = 1L;
    final String ID_GOOGLE = "1";
    final String SPOTIFY_TOKEN = "token";

    UserRequest userRequest;

    UserResponse userResponse;

    UserContextResponse userContextResponse;

    @BeforeEach
    public void setUp() {
        List<ContextResponse> contextResponses = new ArrayList<>();
        userContextResponse = UserContextResponse.builder()
                .contextResponses(contextResponses)
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();

        userRequest = UserRequest.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        userResponse = UserResponse.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();
    }

    @Test
    public void shouldSaveAnUser() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);

        assertNotNull(resultUserEntity);
        assertEquals(CREATED, resultUserEntity.getStatusCode());
    }

    @Test
    public void shouldUpdateSpoityToken() throws ParserException, UserRequestException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        UserRequest updatedUserToken = UserRequest.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .spotifyToken("tokenUpdated")
                .build();

        UserResponse updatedUserTokenResponse = UserResponse.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();

        when(userService.updateSpotifyToken(1L, updatedUserToken)).thenReturn(updatedUserTokenResponse);

        ResponseEntity<UserResponse> resultUserEntity2 = userController.updateSpotifyToken(ID_USER, updatedUserToken);
        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
    }

    @Test
    public void shouldFoundUserById() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        when(userService.findById(ID_USER)).thenReturn(Optional.of(userResponse));
        ResponseEntity<UserResponse> resultUserEntity2 = userController.findById(ID_USER);

        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
        assertEquals(ID_USER, resultUserEntity2.getBody().getIdUser());
        assertEquals(ID_GOOGLE, resultUserEntity2.getBody().getIdGoogle());
    }

    @Test
    public void listGetHaveSizeOne() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        List<UserResponse> userResponseList = Arrays.asList(userResponse);

        when(userService.findAll()).thenReturn(userResponseList);

        ResponseEntity<List<UserResponse>> resultList = userController.findAll();
        assertNotNull(resultList);
        assertEquals(1, resultList.getBody().size());
    }

    @Test
    public void shouldFoundContextUserById() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        when(userService.findByIdWithContext(ID_USER)).thenReturn(Optional.of(userContextResponse));
        ResponseEntity<UserContextResponse> resultUserEntity2 = userController.findByIdWithContext(ID_USER);

        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
        assertEquals(ID_USER, Objects.requireNonNull(resultUserEntity2.getBody()).getIdUser());
        assertEquals(ID_GOOGLE, resultUserEntity2.getBody().getIdGoogle());
    }

    @Test
    public void listGetWithContextHaveSizeOne() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        List<UserContextResponse> userResponseList = Arrays.asList(userContextResponse);

        when(userService.findAllWithContext()).thenReturn(userResponseList);

        ResponseEntity<List<UserContextResponse>> resultList = userController.findAllWithContext();
        assertNotNull(resultList);
        assertEquals(1, Objects.requireNonNull(resultList.getBody()).size());
    }
}
