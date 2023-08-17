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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


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
        long idUser = 12343L;
        TextRequest textRequest = TextRequest.builder().idUser(idUser).build();
        RequestEntity requestEntity = RequestEntity.builder().idUser(idUser).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        TextRequestResponse actualTextRequestResponse = textRequestService.createTextRequest(textRequest);
        verify(requestEntityRepository).save(requestEntity);
        assertNotNull(actualTextRequestResponse);
    }

    @Test
    public void shouldCreateATextRequestWithCorrectValues() throws TextRequestException {
        final long idUser = 12343L;
        final String text = "some text";
        final long idContext = 1234L;

        TextRequest textRequest = TextRequest.builder()
                .idUser(idUser)
                .text(text)
                .idContext(idContext)
                .build();

        RequestEntity requestEntity = RequestEntity.builder()
                .idUser(idUser)
                .text(text)
                .idContext(idContext)
                .build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        TextRequestResponse actualTextRequestResponse = textRequestService.createTextRequest(textRequest);
        verify(requestEntityRepository).save(requestEntity);

        assertNotNull(actualTextRequestResponse);

        assertEquals(actualTextRequestResponse.getIdUser(), idUser);
        assertEquals(actualTextRequestResponse.getText(), text);
        assertEquals(actualTextRequestResponse.getIdContext(), idContext);
    }

    @Test
    public void shouldThrowsAnException() {
        TextRequest textRequest = TextRequest.builder().idUser(null).build();
        RequestEntity requestEntity = RequestEntity.builder().idUser(null).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        requestEntityRepository.save(requestEntity);

        assertThrows(TextRequestException.class, () -> textRequestService.createTextRequest(textRequest));
    }

    @Test
    public void shouldThrowsACorrectExceptionMessage() {
        TextRequest textRequest = TextRequest.builder().idUser(null).build();
        RequestEntity requestEntity = RequestEntity.builder().idUser(null).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        requestEntityRepository.save(requestEntity);

        Exception exceptionExpected = assertThrows(TextRequestException.class, () -> textRequestService.createTextRequest(textRequest));

        String expected = "User id should not be null";
        String actual = exceptionExpected.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void givenRequestEntity_whenSaveTextRequestService_thenSaveTextRequest() {
        String text = "Test Text";
        long idRequest = 123L;
        long idAudio = 456L;
        long idUser = 789L;

        RequestEntity requestEntity = RequestEntity.builder()
                .idRequest(idRequest)
                .text(text)
                .idAudioMongo(idAudio)
                .idUser(idUser)
                .build();

        when(requestEntityRepository.save(any(RequestEntity.class))).thenReturn(requestEntity);

        TextRequest result = textRequestService.save(idRequest, text, idAudio, idUser);

        assertNotNull(result);
        assertSame(result.getText(), requestEntity.getText());
        assertSame(result.getIdUser(), requestEntity.getIdUser());
        assertSame(result.getIdAudioMongo(), requestEntity.getIdAudioMongo());
    }
}
