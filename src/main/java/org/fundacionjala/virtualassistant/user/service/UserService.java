package org.fundacionjala.virtualassistant.user.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.UserParser;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final String INVALID_USER_MESSAGE = "Invalid user";
    private UserRepo userRepo;

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

    public UserResponse save(@NotNull UserRequest userRequest) throws UserRequestException {
        if (null == userRequest || userRequest.getIdUser() < 0) {
            throw new UserRequestException(INVALID_USER_MESSAGE);
        }

        UserEntity userEntity = userRepo.save(
                UserEntity.builder()
                        .idGoogle(userRequest.getIdGoogle())
                        .contextEntities(new ArrayList<>())
                        .build()
        );
        return UserParser.parseFrom(userEntity);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
