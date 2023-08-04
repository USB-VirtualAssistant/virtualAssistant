package org.fundacionjala.virtualassistant.textrequest.controller;

import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TextRequestControllerTest {
  @Mock
  private RequestEntityRepository requestEntityRepository;

  @Mock
  private RequestEntityController requestEntityController;

  @BeforeEach
  public void setUp() {
    TextRequestService textRequestService = new TextRequestService(requestEntityRepository);
  }

  @Test
  public void statusShouldBeCreated() throws TextRequestException {
    TextRequest textRequest = TextRequest.builder().idUser(12343L).build();

    when(requestEntityController.createTextRequest(textRequest)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

    ResponseEntity<TextRequestResponse> actualResponseEntity= requestEntityController.createTextRequest(textRequest);

    Assertions.assertEquals(actualResponseEntity.getStatusCode(), HttpStatus.CREATED);
  }
}
