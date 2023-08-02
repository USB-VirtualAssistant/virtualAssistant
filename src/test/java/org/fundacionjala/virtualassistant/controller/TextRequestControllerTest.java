package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextRequestControllerTest {
    private TextRequestController textRequestController;

    @BeforeEach
    void setUp() {
        textRequestController = mock(TextRequestController.class);
    }

    @Test
    void getTextRequestsById() {
        RequestEntity requestA = new RequestEntity("How's the weather today?", new Date(), 2L, 1L, 2L);
        RequestEntity requestB = new RequestEntity("Hello assistant!", new Date(), 1L, 1L, 2L);

        List<RequestEntity> requests = new ArrayList<>();
        requests.add(requestB);
        requests.add(requestA);

        when(textRequestController.getTextRequests(1L, 2L)).thenReturn(requests);

        assertEquals(textRequestController.getTextRequests(1L, 2L), requests);
    }

}
