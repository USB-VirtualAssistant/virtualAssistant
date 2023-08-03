package org.fundacionjala.virtualassistant.mongo.services;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.exception.GeneratedDocumentException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

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

    public Recording getRecordingToUserChat(String idRecording, Long idUser, Long idChat) {
        return recordingRepo.getRecordingToUser(idRecording, idUser, idChat);
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

    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile) throws GeneratedDocumentException {
        return recordingRepo.saveRecording(idUser,idChat,audioFile);
    }

    private File convertDocumentToFile(Document document, String outputPath){
        try {
            String encodedAudio = document.getString("audio");
            byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
            File outputFile = new File("audio.wav");
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(audioBytes);
            fos.close();
            return outputFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
