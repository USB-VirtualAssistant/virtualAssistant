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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TextRequestControllerTest {
  @Mock
  private RequestEntityRepository requestEntityRepository;

  @Mock
  private RequestEntityController requestEntityController;

  TextRequestService textRequestService;

  @BeforeEach
  public void setUp() {
    textRequestService = new TextRequestService(requestEntityRepository);
  }

  @Test
  public void statusShouldBeCreated() throws TextRequestException {
    TextRequest textRequest = TextRequest.builder()
        .idUser(1234L)
        .text("some")
        .build();
    TextRequestResponse textRequestResponse = TextRequestResponse.builder()
        .idUser(1234L)
        .text("some")
        .build();

    ResponseEntity<TextRequestResponse> expected = new ResponseEntity<>(textRequestResponse, HttpStatus.CREATED);

    when(requestEntityController.createTextRequest(textRequest)).thenReturn(expected);

    ResponseEntity<TextRequestResponse> actualResponseEntity = requestEntityController.createTextRequest(textRequest);

    assertEquals(actualResponseEntity.getStatusCode(), HttpStatus.CREATED);
  }
}
