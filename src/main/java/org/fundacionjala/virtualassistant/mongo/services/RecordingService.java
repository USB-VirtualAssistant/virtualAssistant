package org.fundacionjala.virtualassistant.mongo.services;

import lombok.SneakyThrows;
import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedMultipartFileException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;

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


    public RecordingResponse getRecording(String idRecording) throws RecordingException, ConvertedMultipartFileException {
        Recording recording = recordingRepo.getRecording(idRecording);
        return convertRecordingToResponse(recording);
    }

    public RecordingResponse getRecordingToUserChat(String idRecording, Long idUser, Long idChat) throws RecordingException, ConvertedMultipartFileException {
        Recording recording = recordingRepo.getRecordingToUser(idRecording, idUser, idChat);
        return convertRecordingToResponse(recording);
    }

    public boolean deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    public List<RecordingResponse> getAllRecordingsToUser(Long idUser, Long idChat) throws RecordingException, ConvertedMultipartFileException {
        List<Recording> recordings = recordingRepo.getAllRecordingsToUser(idUser,idChat);
        return convertListRecordingsToListResponse(recordings);
    }

    public List<RecordingResponse> getAllRecordings() throws RecordingException, ConvertedMultipartFileException {
        List<Recording> recordings = recordingRepo.getAllRecordings();
        return  convertListRecordingsToListResponse(recordings);
    }

    public RecordingResponse saveRecording(Long idUser, Long idChat, MultipartFile audioFile) throws RecordingException, ConvertedMultipartFileException {
        Recording recording = recordingRepo.saveRecording(idUser,idChat,audioFile);
        return convertRecordingToResponse(recording);
    }

    private RecordingResponse convertRecordingToResponse(Recording recording) throws RecordingException, ConvertedMultipartFileException {
        return RecordingResponse.builder()
                .idRecording(recording.getIdRecording())
                .idUser(recording.getIdUser())
                .idChat(recording.getIdChat())
                .audioFile(convertDocumentToMultipartFile(recording.getAudioFile()))
                .build();
    }

    private List<RecordingResponse> convertListRecordingsToListResponse(List<Recording> recordings) throws RecordingException, ConvertedMultipartFileException {
        List<RecordingResponse> list = new ArrayList<>();
        for (Recording recording : recordings) {
            RecordingResponse recordingResponse = convertRecordingToResponse(recording);
            list.add(recordingResponse);
        }
        return list;
    }

    public static MultipartFile convertDocumentToMultipartFile(Document document) throws ConvertedMultipartFileException {
        try {
            String encodedAudio = document.getString(AUDIO_FIELD_NAME);
            byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
            return new CustomMultipartFile(audioBytes, AUDIO_FIELD_NAME, "", "wav");
        } catch (IllegalArgumentException e) {
            throw new ConvertedMultipartFileException("Error to decoder the audio Document: " + e.getMessage());
        }
    }

}

