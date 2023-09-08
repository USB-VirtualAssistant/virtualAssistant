package org.fundacionjala.virtualassistant.user.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws UserRequestException{
        UserResponse userResponse = userService.save(userRequest);
        return new ResponseEntity<>(userResponse, CREATED);
    }

}
