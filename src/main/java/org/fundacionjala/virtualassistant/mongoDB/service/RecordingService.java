package org.fundacionjala.virtualassistant.mongoDB.service;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface RecordingService {

    Recording getRecording(String idRecording);

    boolean deleteRecording(String idRecording);

    List<Recording> getAllRecordings();

    List<Recording> getAllRecordingsToUser(Long idUser, Long idChat);

    Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile);

}
