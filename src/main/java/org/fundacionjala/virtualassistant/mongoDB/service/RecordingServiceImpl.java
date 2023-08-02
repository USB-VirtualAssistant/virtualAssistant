package org.fundacionjala.virtualassistant.mongoDB.service;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.fundacionjala.virtualassistant.mongoDB.repository.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RecordingServiceImpl implements RecordingService{

    @Autowired
    RecordingRepo recordingRepo;

    @Override
    public Recording getRecording(String idRecording) {
        return recordingRepo.getRecording(idRecording);
    }

    @Override
    public boolean deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    @Override
    public List<Recording> getAllRecordingsToUser(Long idUser, Long idChat) {
        return recordingRepo.getAllRecordingsToUser(idUser,idChat);
    }

    @Override
    public List<Recording> getAllRecordings() {
        return recordingRepo.getAllRecordings();
    }

    @Override
    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile) {
        return recordingRepo.saveRecording(idUser,idChat,audioFile);
    }
}
