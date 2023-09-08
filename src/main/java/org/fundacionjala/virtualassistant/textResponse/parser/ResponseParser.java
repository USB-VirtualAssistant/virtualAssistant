package org.fundacionjala.virtualassistant.textResponse.parser;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;

import java.time.ZonedDateTime;

public class ResponseParser {
    public static TextResponse parseFrom(ResponseEntity responseEntity) {
        return TextResponse.builder()
                .idRequest(responseEntity.getRequestEntity().getIdRequest())
                .idResponse(responseEntity.getIdResponse())
                .text(responseEntity.getText())
                .date(responseEntity.getDate())
                .build();
    }

    public static TextResponse parseWithOutIdFrom(ResponseEntity responseEntity) {
        return TextResponse.builder()
                .idResponse(responseEntity.getIdResponse())
                .text(responseEntity.getText())
                .date(responseEntity.getDate())
                .build();
    }

    public static ResponseEntity parseFrom(ParameterResponse parameterResponse) {
        return ResponseEntity.builder()
                .text(parameterResponse.getText())
                .date(ZonedDateTime.now())
                .requestEntity(parseFrom(parameterResponse.getRequest()))
                .build();
    }

    public static RequestEntity parseFrom(TextRequestResponse textRequestResponse) {
        return RequestEntity.builder()
                .idRequest(textRequestResponse.getIdRequest())
                .build();
    }

}
