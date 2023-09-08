package org.fundacionjala.virtualassistant.context.parser;

import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.fundacionjala.virtualassistant.user.controller.parser.UserParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContextParser {
    public static ContextResponse parseFrom(ContextEntity context) {
        return ContextResponse.builder()
                .idContext(context.getIdContext())
                .title(context.getTitle())
                .idUser(context.getUserEntity().getIdUser())
                .build();
    }

    @NotNull
    private static List<TextRequestResponse> parseRequestEntities(ContextEntity context) {
        return context.getRequestEntities().stream()
                .map(TextRequestParser::parseFrom)
                .collect(Collectors.toList());
    }

    public static ContextEntity parseFrom(ContextRequest contextRequest) {
        return ContextEntity.builder()
                .title(contextRequest.getTitle())
                .userEntity(UserParser.parseFrom(contextRequest.getUserRequest()))
                .requestEntities(new ArrayList<>())
                .build();
    }

    public static ContextEntity parseFrom(ContextResponse contextResponse) {
        return ContextEntity.builder()
                .idContext(contextResponse.getIdContext())
                .build();
    }
}
