package org.fundacionjala.virtualassistant.services.mongo;

import org.bson.Document;
import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.fundacionjala.virtualassistant.repository.mongo.RecordingRepo;
import org.fundacionjala.virtualassistant.repository.mongo.RecordingRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
  void saveRecordingInDB() {
    long idUser = 12L;
    long idChat = 13L;
    var mockFile = new MockMultipartFile("test", new byte[10]);

    Recording recording = new Recording(idUser, idChat, new Document("test", "test"));
    when(service.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenReturn(recording);

    Recording result = service.saveRecording(idUser, idChat, mockFile);
    verify(recordingRepo).saveRecording(idUser, idChat, mockFile);
    assertNotNull(result);
    assertEquals(recording, result);
  }
}