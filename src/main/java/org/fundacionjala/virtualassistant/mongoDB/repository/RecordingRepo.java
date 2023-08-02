package org.fundacionjala.virtualassistant.mongoDB.repository;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordingRepo {

    Recording getRecording(Long idRecording);

    boolean deleteRecording(Long idRecording);

    List<Recording> getAllRecordingsToUser(Long idUser, Long idChat);

    List<Recording> getAllRecordings();

    Recording saveRecording(Long idUser, Long idChat, MultipartFile audioPath);

}
