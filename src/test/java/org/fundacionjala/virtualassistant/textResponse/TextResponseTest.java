package org.fundacionjala.virtualassistant.textResponse;

import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.textResponse.repository.ResponseEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextResponseTest {
    @Test
    public void givenResponseEntity_whenSaveTextResponseService_thenSaveTextResponse(){
        ResponseEntityRepository mockRepository = mock(ResponseEntityRepository.class);

        TextResponseService textResponseService = new TextResponseService(mockRepository);

        String text = "Test Text";
        long idRequest = 123L;

        ResponseEntity responseEntity = ResponseEntity.builder()
                .idRequest(idRequest)
                .text(text)
                .date(ZonedDateTime.now())
                .build();

        when(mockRepository.save(any(ResponseEntity.class))).thenReturn(responseEntity);

        TextResponse result = textResponseService.save(idRequest,text);

        assertNotNull(result);
        assertSame(result.getText(), responseEntity.getText());
        assertSame(result.getIdResponse(),responseEntity.getIdResponse());
        assertSame(result.getIdRequest(),responseEntity.getIdRequest());
    }
}
