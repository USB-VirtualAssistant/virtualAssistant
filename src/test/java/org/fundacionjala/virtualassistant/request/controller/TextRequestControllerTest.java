package org.fundacionjala.virtualassistant.request.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.textResponse.parser.ResponseParser;
import org.fundacionjala.virtualassistant.textrequest.controller.RequestEntityController;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextRequestControllerTest {
    private RequestEntityController textRequestController;
    private static final Long CONTEXT_ID_1 = 1L;
    private static final Long USER_ID_1 = 2L;
    private static final Long CONTEXT_ID_2 = 2L;
    private static final Long USER_ID_2 = 1L;
    private RequestEntity requestA;
    private RequestEntity requestB;

    @BeforeEach
    void setUp() {
        textRequestController = mock(RequestEntityController.class);
        requestA = new RequestEntity();
        requestB = new RequestEntity();
    }

    @Test
    void getTextRequestsById() {
        List<RequestEntity> requests = new ArrayList<>();
        requests.add(requestB);
        requests.add(requestA);

        List<TextRequestResponse> requestResponses =
                requests.stream()
                        .map(TextRequestParser::parseFrom)
                        .collect(Collectors.toList());

        when(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1))
                .thenReturn(ResponseEntity.ok(requestResponses));

        assertEquals(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1).getBody(), requestResponses);
    }

    @Test
    void getTextRequestsByIdWithAnotherValuesOfId() {

        List<RequestEntity> requests = new ArrayList<>();
        requests.add(requestB);
        requests.add(requestA);
        List<TextRequestResponse> requestResponses =
                requests.stream()
                        .map(TextRequestParser::parseFrom)
                        .collect(Collectors.toList());

        when(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2))
                .thenReturn(ResponseEntity.ok(requestResponses));

        assertEquals(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2).getBody(), requestResponses);
    }
}