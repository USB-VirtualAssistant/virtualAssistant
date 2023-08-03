package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
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
    private TextRequestController textRequestController;

    private static final String REQUEST_TEXT_A = "How's the weather today?";
    private static final String REQUEST_TEXT_B = "Hello assistant!";
    private static final Long ID_AUDIO_A = 2L;
    private static final Long ID_AUDIO_B = 1L;
    private static final Long CONTEXT_ID = 1L;
    private static final Long USER_ID = 2L;
    private static final ZonedDateTime REQUEST_DATE = ZonedDateTime.of(2023, 8, 2, 12, 0, 0, 0, ZoneId.systemDefault());

    @BeforeEach
    void setUp() {
        textRequestController = mock(TextRequestController.class);
    }

    @Test
    void getTextRequestsById() {
        RequestEntity requestA = new RequestEntity(REQUEST_TEXT_A, REQUEST_DATE, ID_AUDIO_A, CONTEXT_ID, USER_ID);
        RequestEntity requestB = new RequestEntity(REQUEST_TEXT_B, REQUEST_DATE, ID_AUDIO_B, CONTEXT_ID, USER_ID);

        List<RequestEntity> requests = new ArrayList<>();
        requests.add(requestB);
        requests.add(requestA);

        when(textRequestController.getTextRequests(1L, 2L)).thenReturn(requests);

        assertEquals(textRequestController.getTextRequests(1L, 2L), requests);
    }

}
