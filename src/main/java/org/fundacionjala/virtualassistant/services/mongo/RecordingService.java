package org.fundacionjala.virtualassistant.services.mongo;

import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface RecordingService {

    Recording getRecording(String idRecording);

    boolean deleteRecording(String idRecording);

    List<Recording> getAllRecordings();

    List<Recording> getAllRecordingsToUser(Long idUser, Long idChat);

    Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile);

}
