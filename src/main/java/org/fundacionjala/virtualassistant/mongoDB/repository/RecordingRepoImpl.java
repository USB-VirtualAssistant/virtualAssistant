package org.fundacionjala.virtualassistant.mongoDB.repository;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;

import java.util.List;

public class RecordingRepoImpl implements RecordingRepo{

    @Override
    public Recording getRecording(Long idRecording) {
        return null;
    }

    @Override
    public boolean deleteRecording(Long idRecording) {
        return false;
    }

    @Override
    public List<Recording> getAllRecordings(Long idUser, Long idChat) {
        return null;
    }
}
