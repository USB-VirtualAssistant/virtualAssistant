package org.fundacionjala.virtualassistant.mongoDB.service;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;

import java.util.List;

public interface RecordingService {

    Recording getRecording(Long idRecording);

    boolean deleteRecording(Long idRecording);

    List<Recording> getAllRecordings(Long idUser, Long idChat);

    Recording saveRecording(Long idUser, Long idChat, String audioPath);
}
