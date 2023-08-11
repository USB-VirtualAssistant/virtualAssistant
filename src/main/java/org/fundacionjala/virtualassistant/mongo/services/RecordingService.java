package org.fundacionjala.virtualassistant.mongo.services;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedDocumentToFileException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class RecordingService {

    private static final String AUDIO_FIELD_NAME = "audio";
    private static final String MESSAGE_EITHER_NULL = "The either processor is null";
    RecordingRepo recordingRepo;
    ProcessorEither<Exception, RecordingResponse> processorEither;

    private static final String AUDIO_FIELD_NAME = "audio";
    private static final String MESSAGE_NOT_WAV = "The provided file is not a valid .wav file";
    private static final String AUDIO_EXTENSION = ".wav";
    private static final String AUDIO_CONTENT_TYPE = "audio/wav";

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

    public long deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    public List<RecordingResponse> getAllRecordingsToUser(Long idUser, Long idChat)
            throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordingsToUser(idUser,idChat);
        return convertListRecordingsToListResponse(recordings);
    }

    public List<RecordingResponse> getAllRecordings() throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordings();
        return  convertListRecordingsToListResponse(recordings);
    }

    public RecordingResponse saveRecording(RecordingRequest request) throws RecordingException {
        if (!validateWavFile(request.getAudioFile())) {
            throw new RecordingException(MESSAGE_NOT_WAV);
        }
        Recording recording = recordingRepo.saveRecording(request.getIdUser(), request.getIdChat(), request.getAudioFile());
        return convertRecordingToResponse(recording);
    }

    private RecordingResponse convertRecordingToResponse(Recording recording) throws RecordingException {
        if (isNull(recording)) {
            throw new RecordingException(RecordingException.MESSAGE_RECORDING_NULL);
        }
        return RecordingResponse.builder()
                .idRecording(recording.getIdRecording())
                .idUser(recording.getIdUser())
                .idChat(recording.getIdChat())
                .audioFile(convertDocumentToFile(recording.getAudioFile(), AUDIO_FIELD_NAME))
                .build();
    }

    private List<RecordingResponse> convertListRecordingsToListResponse(List<Recording> recordings)
            throws RecordingException {
        if (isNull(processorEither)) {
            throw new RecordingException(MESSAGE_EITHER_NULL);
        }
        return recordings.stream()
                .map(processorEither.lift(recording -> {
                    try {
                        return Either.right(convertRecordingToResponse(recording));
                    } catch (RecordingException exception) {
                        return Either.left(exception);
                    }
                }))
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    private File convertDocumentToFile(Document document, String nameAudio) throws ConvertedDocumentToFileException {
        try {
            String encodedAudio = document.getString(AUDIO_FIELD_NAME);
            byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
            File outputFile =  File.createTempFile(nameAudio, AUDIO_EXTENSION);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(audioBytes);
            fos.close();
            return outputFile;
        } catch (IOException e) {
            throw new ConvertedDocumentToFileException(e.getMessage(), e);
        }
    }

    private boolean validateWavFile(MultipartFile audioFile) throws RecordingException {
        String fileOriginName = Objects.requireNonNull(audioFile.getOriginalFilename());
        String contentType = audioFile.getContentType();
        System.out.println("0 " + audioFile.getOriginalFilename());
        System.out.println("2 " + fileOriginName.endsWith(AUDIO_EXTENSION));
        System.out.println( "1 "+ Objects.equals(contentType, AUDIO_CONTENT_TYPE));
        return fileOriginName.endsWith(AUDIO_EXTENSION) && Objects.equals(contentType, AUDIO_CONTENT_TYPE);
    }
}

