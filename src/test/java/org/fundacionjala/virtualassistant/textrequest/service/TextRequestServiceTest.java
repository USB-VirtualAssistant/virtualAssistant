package org.fundacionjala.virtualassistant.textrequest.service;

import org.fundacionjala.virtualassistant.models.RequestEntity;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TextRequestServiceTest {

    @Mock
    private RequestEntityRepository requestEntityRepository;

    private TextRequestService textRequestService;

    @BeforeEach
    public void setup() {
        requestEntityRepository = mock(RequestEntityRepository.class);
        textRequestService = new TextRequestService(requestEntityRepository);
    }

    @Test
    public void shouldCreateATextRequest() throws TextRequestException {
        long idUser =12343L;
        TextRequest textRequest = TextRequest.builder().idUser(idUser).build();
        RequestEntity requestEntity = RequestEntity.builder().idUser(idUser).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        TextRequestResponse actualTextRequestResponse = textRequestService.createTextRequest(textRequest);
        verify(requestEntityRepository).save(requestEntity);
        assertNotNull(actualTextRequestResponse);
    }

    @Test
    public void shouldReturnAnException() {
        TextRequest textRequest = TextRequest.builder().idUser(null).build();
        RequestEntity requestEntity = RequestEntity.builder().idUser(null).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        requestEntityRepository.save(requestEntity);

        assertThrows(TextRequestException.class, () -> textRequestService.createTextRequest(textRequest));
    }
}
