package org.fundacionjala.virtualassistant.mongoDB.service;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.fundacionjala.virtualassistant.mongoDB.repository.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecordingServiceImpl implements RecordingService{

    @Autowired
    RecordingRepo recordingRepo;

    @Override
    public Recording getRecording(Long idRecording) {
        return recordingRepo.getRecording(idRecording);
    }

    @Override
    public boolean deleteRecording(Long idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    @Override
    public List<Recording> getAllRecordings(Long idUser, Long idChat) {
        return recordingRepo.getAllRecordings(idUser, idChat);
    }

    @Override
    public Recording saveRecording(Long idUser, Long idChat, String audioPath) {
        return null;
    }
}
