package org.fundacionjala.virtualassistant.services.mongo;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepositoryImpl;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecordingServiceImplTest {
  private RecordingService service;
  private RecordingRepo recordingRepo;

  @BeforeEach
  void setUp() {
    recordingRepo = mock(RecordingRepositoryImpl.class);
    service = new RecordingService(recordingRepo);
  }

  @Test
  void saveRecordingInDB() throws RecordingException, IOException {
    long idUser = 12L;
    long idChat = 13L;
    var mockFile = new MockMultipartFile("test", new byte[10]);

    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    File file = File.createTempFile("tem_", mockFile.getContentType());
    mockFile.transferTo(file);

    RecordingResponse recordingResponse = new RecordingResponse("Id", idUser, idChat, file);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenReturn(new Recording(idUser, idChat, new Document("audio", "")));

    RecordingResponse result = service.saveRecording(recordingRequest);
    verify(recordingRepo).saveRecording(idUser, idChat, mockFile);
    assertNotNull(result);
    assertEquals(recordingResponse.getIdUser(), result.getIdUser());
    assertEquals(recordingResponse.getIdChat(), result.getIdChat());
    assertNotNull(result.getAudioFile());
  }
}