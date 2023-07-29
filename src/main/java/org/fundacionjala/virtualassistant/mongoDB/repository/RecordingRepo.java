package org.fundacionjala.virtualassistant.mongoDB.repository;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;

import java.util.List;

public interface RecordingRepo {

    Recording getRecording(Long idRecording);

    boolean deleteRecording(Long idRecording);

    List<Recording> getAllRecordings(Long idUser, Long idChat);
}
