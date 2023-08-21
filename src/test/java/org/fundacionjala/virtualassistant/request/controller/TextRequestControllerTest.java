package org.fundacionjala.virtualassistant.request.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.textrequest.controller.RequestEntityController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextRequestControllerTest {
    private RequestEntityController textRequestController;

    private static final String REQUEST_TEXT_A = "How's the weather today?";
    private static final String REQUEST_TEXT_B = "Hello assistant!";
    private static final String ID_AUDIO_A = "mongo_id";
    private static final String ID_AUDIO_B = "mongo_id";
    private static final Long CONTEXT_ID_1 = 1L;
    private static final Long USER_ID_1 = 2L;
    private static final Long CONTEXT_ID_2 = 2L;
    private static final Long USER_ID_2 = 1L;
    private static final ZonedDateTime REQUEST_DATE = ZonedDateTime.of(2023, 8, 2, 12,
            0, 0, 0, ZoneId.systemDefault());
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

        when(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1)).thenReturn(requests);

        assertEquals(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1), requests);
    }

    @Test
    void getTextRequestsByIdWithAnotherValuesOfId() {

        List<RequestEntity> requests = new ArrayList<>();
        requests.add(requestB);
        requests.add(requestA);

        when(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2)).thenReturn(requests);

        assertEquals(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2), requests);
    }
}