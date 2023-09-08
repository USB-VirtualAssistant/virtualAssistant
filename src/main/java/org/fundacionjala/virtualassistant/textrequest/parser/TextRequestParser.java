package org.fundacionjala.virtualassistant.textrequest.parser;

import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.textResponse.parser.ResponseParser;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;

public class TextRequestParser {
    public static RequestEntity parseFrom(TextRequest textRequest) {
        return RequestEntity.builder()
                .idUser(textRequest.getIdUser())
                .text(textRequest.getText())
                .idAudioMongo(textRequest.getIdAudioMongo())
                .contextEntity(ContextParser.parseFrom(textRequest.getContextResponse()))
                .date(textRequest.getDate())
                .build();
    }

    public static TextRequestResponse parseFrom(RequestEntity requestEntity) {
        return TextRequestResponse.builder()
                .idRequest(requestEntity.getIdRequest())
                .idUser(requestEntity.getIdUser())
                .idContext(requestEntity.getContextEntity().getIdContext())
                .text(requestEntity.getText())
                .date(requestEntity.getDate())
                .textResponse(ResponseParser.parseWithOutIdFrom(requestEntity.getResponseEntity()))
                .build();
    }
}
