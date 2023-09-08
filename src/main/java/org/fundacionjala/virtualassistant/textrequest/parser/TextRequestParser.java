package org.fundacionjala.virtualassistant.textrequest.parser;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;

public class TextRequestParser {
    public static RequestEntity parseFrom(TextRequest textRequest) {
        return RequestEntity.builder()
                .idUser(textRequest.getIdUser())
                .text(textRequest.getText())
                .idAudioMongo(textRequest.getIdAudioMongo())
                .idContext(textRequest.getIdContext())
                .date(textRequest.getDate())
                .build();
    }

    public static TextRequestResponse parseFrom(RequestEntity requestEntity) {
        return TextRequestResponse.builder()
                .idRequest(requestEntity.getIdRequest())
                .idUser(requestEntity.getIdUser())
                .idContext(requestEntity.getIdContext())
                .text(requestEntity.getText())
                .date(requestEntity.getDate())
                .build();
    }
}
