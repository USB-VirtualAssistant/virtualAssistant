package org.fundacionjala.virtualassistant.mongoDB.repository;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.List;

public class RecordingRepoImpl implements RecordingRepo{

    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public RecordingRepoImpl(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

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

    @Override
    public Recording saveRecording(Long idUser, Long idChat, String audioPath) {
        return null;
    }
}
