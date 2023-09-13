package org.fundacionjala.virtualassistant.user.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.parser.exception.ContextParserException;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.controller.parser.UserParser;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserParserException;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.fundacionjala.virtualassistant.util.either.Either;
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
    private static final Either<Exception, UserResponse> either = new Either<>();
    private static final Either<Exception, UserContextResponse> eitherContextResponse = new Either<>();

    public List<UserResponse> findAll() {
        return userRepo.findAll().stream()
                .map(either.lift(userEntity -> {
                    try {
                        return Either.right(UserParser.parseFrom(userEntity));
                    } catch (UserParserException e) {
                        return Either.left(e);
                    }
                }))
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    public List<UserContextResponse> findAllWithContext() {
        return userRepo.findAllEager().stream()
                .map(eitherContextResponse.lift(userEntity -> {
                    try {
                        return Either.right(UserParser.parseFromWithContext(userEntity));
                    } catch (ContextParserException | UserParserException e) {
                        return Either.left(e);
                    }
                }))
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> findById(@NotNull Long id) throws UserRequestException, UserParserException {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }
        var userResponse = UserParser.parseFrom(optionalUserEntity.get());
        return Optional.of(userResponse);
    }

    public Optional<UserContextResponse> findByIdWithContext(@NotNull Long id)
            throws UserParserException, ContextParserException {
        Optional<UserEntity> optionalUserEntity = userRepo.findByIdUser(id);
        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }
        UserContextResponse userContextResponse = UserParser.parseFromWithContext(optionalUserEntity.get());
        return Optional.of(userContextResponse);
    }

    public UserResponse save(@NotNull UserRequest userRequest) throws UserParserException {
        UserEntity userEntity = userRepo.save(UserParser.parseFrom(userRequest));
        return UserParser.parseFrom(userEntity);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserResponse updateSpotifyToken(@PathVariable Long id, @NotNull UserRequest userRequest)
            throws UserRequestException, UserParserException {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new UserRequestException(NOT_FOUND_USER + id);
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setSpotifyToken(userRequest.getSpotifyToken());
        UserEntity userSaved = userRepo.save(userEntity);
        return UserParser.parseFrom(userSaved);
    }
}
