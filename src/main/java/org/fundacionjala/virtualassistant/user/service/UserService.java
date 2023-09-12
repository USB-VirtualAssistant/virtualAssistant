package org.fundacionjala.virtualassistant.user.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.controller.parser.UserParser;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private static final String NOT_FOUND_USER = "Not found user";

    public List<UserResponse> findAll() {
        return userRepo.findAll().stream()
                .map(UserParser::parseFrom)
                .collect(Collectors.toList());
    }

    public List<UserContextResponse> findAllWithContext() {
        return userRepo.findAllEager().stream()
                .map(UserParser::parseFromWithContext)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> findById(@NotNull Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        return optionalUserEntity.map(UserParser::parseFrom);
    }

    public Optional<UserContextResponse> findByIdWithContext(@NotNull Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByIdUser(id);
        return optionalUserEntity.map(UserParser::parseFromWithContext);
    }

    public UserResponse save(@NotNull UserRequest userRequest) {
        UserEntity userEntity = userRepo.save(UserParser.parseFrom(userRequest));
        return UserParser.parseFrom(userEntity);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserResponse updateSpotifyToken(@PathVariable Long id, @NotNull UserRequest userRequest)
            throws UserRequestException {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new UserRequestException(NOT_FOUND_USER);
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setSpotifyToken(userRequest.getSpotifyToken());
        UserEntity userSaved = userRepo.save(userEntity);
        return UserParser.parseFrom(userSaved);
    }
}
