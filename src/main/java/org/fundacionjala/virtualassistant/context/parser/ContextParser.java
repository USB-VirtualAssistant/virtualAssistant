package org.fundacionjala.virtualassistant.context.parser;

import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.context.parser.exception.ContextParserException;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.fundacionjala.virtualassistant.user.controller.parser.UserParser;
import org.fundacionjala.virtualassistant.user.exception.UserParserException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class ContextParser {
    public static ContextResponse parseFrom(ContextEntity context) throws ContextParserException {
        if (isNull(context)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_ENTITY);
        }
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

    public static ContextEntity parseFrom(ContextRequest contextRequest)
            throws ContextParserException, UserParserException {
        if (isNull(contextRequest)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_REQUEST);
        }
        return ContextEntity.builder()
                .title(contextRequest.getTitle())
                .userEntity(UserParser.parseFrom(contextRequest.getUserRequest()))
                .requestEntities(new ArrayList<>())
                .build();
    }

    public static ContextEntity parseFrom(ContextResponse contextResponse) throws ContextParserException {
        if (isNull(contextResponse)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_RESPONSE);
        }
        return ContextEntity.builder()
                .idContext(contextResponse.getIdContext())
                .build();
    }

    public static ContextEntity parseFrom(Long idContext, ContextRequest contextRequest)
            throws UserParserException, ContextParserException {
        if (isNull(contextRequest)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_REQUEST);
        }
        return ContextEntity.builder()
                .title(contextRequest.getTitle())
                .idContext(idContext)
                .userEntity(UserParser.parseFrom(contextRequest.getUserRequest()))
                .build();
    }
}
