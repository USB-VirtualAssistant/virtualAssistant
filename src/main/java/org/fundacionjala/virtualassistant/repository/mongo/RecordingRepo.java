package org.fundacionjala.virtualassistant.repository.mongo;

import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordingRepo {

    Recording getRecording(String idRecording);

    boolean deleteRecording(String idRecording);

    List<Recording> getAllRecordingsToUser(Long idUser, Long idChat);

    List<Recording> getAllRecordings();

    Recording saveRecording(Long idUser, Long idChat, MultipartFile audioPath);

}