package org.fundacionjala.virtualassistant.services.mongo;

import org.fundacionjala.virtualassistant.models.mongo.Recording;
import org.fundacionjala.virtualassistant.repository.mongo.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RecordingService {

    @Autowired
    RecordingRepo recordingRepo;

    public RecordingService(RecordingRepo recordingRepo) {
        this.recordingRepo = recordingRepo;
    }


    public Recording getRecording(String idRecording) {
        return recordingRepo.getRecording(idRecording);
    }


    public boolean deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }


    public List<Recording> getAllRecordingsToUser(Long idUser, Long idChat) {
        return recordingRepo.getAllRecordingsToUser(idUser,idChat);
    }


    public List<Recording> getAllRecordings() {
        return recordingRepo.getAllRecordings();
    }


    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile) {
        return recordingRepo.saveRecording(idUser,idChat,audioFile);
    }
}
