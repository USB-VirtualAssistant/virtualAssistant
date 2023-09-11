package org.fundacionjala.virtualassistant.user.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.save(userRequest);
        return new ResponseEntity<>(userResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateSpotifyToken(@PathVariable Long id, @RequestBody UserRequest userRequest) throws UserRequestException {
        UserResponse userResponse = userService.updateSpotifyToken(id, userRequest);
        return new ResponseEntity<>(userResponse, OK);
    }
}
