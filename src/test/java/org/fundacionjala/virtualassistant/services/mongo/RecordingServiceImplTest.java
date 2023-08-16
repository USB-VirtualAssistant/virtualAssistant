package org.fundacionjala.virtualassistant.services.mongo;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedDocumentToFileException;
import org.fundacionjala.virtualassistant.mongo.exception.GeneratedDocumentException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepositoryImpl;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordingServiceImplTest {
  private RecordingService service;
  private RecordingRepo recordingRepo;
  long idUser;
  long idChat;
  MockMultipartFile mockFile;

  @BeforeEach
  void setUp() {
    idUser = 12L;
    idChat = 13L;
    mockFile = new MockMultipartFile("test", new byte[10]);
    recordingRepo = mock(RecordingRepositoryImpl.class);
    service = new RecordingService(recordingRepo, new Either<>());
  }

  @Test
  void saveRecordingInDB() throws RecordingException, IOException {
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

  @Test
  void testThrowExceptionToGenerateDocumentFromRecording() throws RecordingException {
    String message = "Not possible to generate the document";
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(new GeneratedDocumentException(message));

    RecordingException recordingException = assertThrows(GeneratedDocumentException.class, () -> {
      service.saveRecording(recordingRequest);
    });
    assertEquals(message, recordingException.getMessage());
  }

  @Test
  void testThrowExceptionRelatedToSavingRecording() throws RecordingException {
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(RecordingException.class);

    assertThrows(RecordingException.class, () -> {
      service.saveRecording(recordingRequest);
    });
  }

  @Test
  void testThrowExceptionConvertedDocumentToFile() throws RecordingException {
    RecordingService serviceMock = mock(RecordingService.class);
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(serviceMock.saveRecording(any(RecordingRequest.class)))
            .thenThrow(ConvertedDocumentToFileException.class);

    assertThrows(ConvertedDocumentToFileException.class, () -> {
      serviceMock.saveRecording(recordingRequest);
    });
  }

  @Test
  void testSaveNullRecording() throws RecordingException {
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, null);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(RecordingException.class);
    assertThrows(RecordingException.class, () -> {
      service.saveRecording(recordingRequest);
    });
  }

  @Test
  void saveRecordingInDBWith1000Bytes() throws RecordingException, IOException {
    mockFile = new MockMultipartFile("test", new byte[1000]);
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    File file = File.createTempFile("tem_", mockFile.getContentType());
    mockFile.transferTo(file);

    RecordingResponse recordingResponse = new RecordingResponse("Id", idUser, idChat, file);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenReturn(new Recording(idUser, idChat, new Document("audio", "")));

    RecordingResponse result = service.saveRecording(recordingRequest);
    
    verify(recordingRepo).saveRecording(idUser, idChat, mockFile);
    assertNotNull(result);
    assertTrue(result.getAudioFile().isFile());
    assertTrue(result.getAudioFile().exists());
    assertEquals(recordingResponse.getIdUser(), result.getIdUser());
    assertEquals(recordingResponse.getIdChat(), result.getIdChat());
    assertNotNull(result.getAudioFile());
  }
}