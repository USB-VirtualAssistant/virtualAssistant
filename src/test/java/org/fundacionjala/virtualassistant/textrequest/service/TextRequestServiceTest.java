package org.fundacionjala.virtualassistant.textrequest.service;

import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextRequestServiceTest {

    @Mock
    private RequestEntityRepository requestEntityRepository;

    private TextRequestService textRequestService;

    @BeforeEach
    public void setup() {
        textRequestService = new TextRequestService(requestEntityRepository);
    }

    @Test
    public void shouldCreateATextRequest() throws TextRequestException {

        TextRequest textRequest = TextRequest.builder().idUser(12343L).build();
        when(requestEntityRepository.save(any())).thenReturn(any());
        TextRequestResponse actualTextRequestResponse = textRequestService.createTextRequest(textRequest);
        assertNotNull(actualTextRequestResponse);
    }
}
