package org.fundacionjala.virtualassistant.user.controller.parser;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserParser {

    private static final ProcessorEither<Exception, ContextResponse> either = new Either<>();

    public static UserResponse parseFrom(UserEntity userEntity) {
        return UserResponse.builder()
                .idUser(userEntity.getIdUser())
                .idGoogle(userEntity.getIdGoogle())
                .build();
    }

    public static UserContextResponse parseFromWithContext(UserEntity userEntity) {
        return UserContextResponse.builder()
                .idUser(userEntity.getIdUser())
                .idGoogle(userEntity.getIdGoogle())
                .contextResponses(parseFrom(userEntity.getContextEntities()))
                .build();
    }

    public static List<ContextResponse> parseFrom(List<ContextEntity> contextEntities) {
        return getContextResponses(contextEntities);
    }

    @NotNull
    public static List<ContextResponse> getContextResponses(List<ContextEntity> contextEntities) {
        return contextEntities.stream()
                .map(
                        either.lift(contextEntity -> Either.right(ContextParser.parseFrom(contextEntity)))
                )
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    public static UserEntity parseFrom(UserRequest userRequest) {
        return UserEntity.builder()
                .idUser(userRequest.getIdUser())
                .idGoogle(userRequest.getIdGoogle())
                .contextEntities(new ArrayList<>())
                .build();
    }
}
