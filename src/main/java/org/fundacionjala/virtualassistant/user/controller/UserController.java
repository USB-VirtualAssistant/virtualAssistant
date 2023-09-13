package org.fundacionjala.virtualassistant.user.controller;

import org.fundacionjala.virtualassistant.user.exception.UserParserException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws UserParserException {
        UserResponse userResponse = userService.save(userRequest);
        return new ResponseEntity<>(userResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateSpotifyToken(@PathVariable Long id, @RequestBody UserRequest userRequest) throws UserRequestException, UserParserException {
        UserResponse userResponse = userService.updateSpotifyToken(id, userRequest);
        return new ResponseEntity<>(userResponse, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) throws UserRequestException, UserParserException {
        return new ResponseEntity<UserResponse>(userService.findById(id).get(), OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<List<UserResponse>>(userService.findAll(), OK);
    }

    @GetMapping("/context/{id}")
    public ResponseEntity<UserContextResponse> findByIdWithContext(@PathVariable Long id) throws UserRequestException {
        return new ResponseEntity<UserContextResponse>(userService.findByIdWithContext(id).get(), OK);
    }

    @GetMapping("/context/list")
    public ResponseEntity<List<UserContextResponse>> findAllWithContext() {
        return new ResponseEntity<List<UserContextResponse>>(userService.findAllWithContext(), OK);
    }
}
