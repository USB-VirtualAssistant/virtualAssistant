package org.fundacionjala.virtualassistant.user.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.UserParser;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
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

    public Optional<UserResponse> findById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        return optionalUserEntity.map(UserParser::parseFrom);
    }

    public Optional<UserContextResponse> findByIdWithContext(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByIdUser(id);
        return optionalUserEntity.map(UserParser::parseFromWithContext);
    }

    public UserResponse save(UserRequest userRequest) {
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
