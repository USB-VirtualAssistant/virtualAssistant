package org.fundacionjala.virtualassistant.mongo.services;

import lombok.SneakyThrows;
import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedDocumentToFileException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class RecordingService {

    @Autowired
    RecordingRepo recordingRepo;

    private static final String AUDIO_FIELD_NAME = "audio";

    public RecordingService(RecordingRepo recordingRepo) {
        this.recordingRepo = recordingRepo;
    }


    public RecordingResponse getRecording(String idRecording) throws RecordingException {
        Recording recording = recordingRepo.getRecording(idRecording);
        return convertRecordingToResponse(recording);
    }

    public RecordingResponse getRecordingToUserChat(String idRecording, Long idUser, Long idChat) throws RecordingException {
        Recording recording = recordingRepo.getRecordingToUser(idRecording, idUser, idChat);
        return convertRecordingToResponse(recording);
    }

    public boolean deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    public List<RecordingResponse> getAllRecordingsToUser(Long idUser, Long idChat) throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordingsToUser(idUser,idChat);
        return convertListRecordingsToListResponse(recordings);
    }

    public List<RecordingResponse> getAllRecordings() throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordings();
        return  convertListRecordingsToListResponse(recordings);
    }

    public RecordingResponse saveRecording(RecordingRequest request) throws RecordingException {
        Recording recording = recordingRepo.saveRecording(request.getIdUser(), request.getIdChat(), request.getAudioFile());
        return convertRecordingToResponse(recording);
    }

    private RecordingResponse convertRecordingToResponse(Recording recording) throws RecordingException {
        return RecordingResponse.builder()
                .idRecording(recording.getIdRecording())
                .idUser(recording.getIdUser())
                .idChat(recording.getIdChat())
                .audioFile(convertDocumentToFile(recording.getAudioFile(),"audio.wav"))
                .build();
    }

    private List<RecordingResponse> convertListRecordingsToListResponse(List<Recording> recordings) throws RecordingException {
        List<RecordingResponse> list = new ArrayList<>();
        for (Recording recording : recordings) {
            RecordingResponse recordingResponse = convertRecordingToResponse(recording);
            list.add(recordingResponse);
        }
        return list;
    }

    private File convertDocumentToFile(Document document, String outputPath) throws ConvertedDocumentToFileException {
        try {
            String encodedAudio = document.getString("audio");
            byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
            File outputFile = new File(outputPath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(audioBytes);
            fos.close();
            return outputFile;
        } catch (IOException e) {
            throw new ConvertedDocumentToFileException(e.getMessage());
        }
    }
}

