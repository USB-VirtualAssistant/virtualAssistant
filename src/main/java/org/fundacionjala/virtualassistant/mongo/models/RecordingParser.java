package org.fundacionjala.virtualassistant.mongo.models;

import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedDocumentToFileException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import static java.util.Objects.isNull;

public class RecordingParser {

    private static final String AUDIO_FIELD_NAME = "audio";
    private static final String AUDIO_EXTENSION = ".wav";

    public static RecordingResponse parseToRecordingResponseFrom(Recording recording)
            throws RecordingException {
        if (isNull(recording)) {
            throw new RecordingException(RecordingException.MESSAGE_RECORDING_NULL);
        }
        return RecordingResponse.builder()
                .idRecording(recording.getIdRecording())
                .idUser(recording.getIdUser())
                .idChat(recording.getIdChat())
                .audioFile(convertDocumentToFile(recording.getAudioFile()))
                .build();
    }

    private static File convertDocumentToFile(org.bson.Document document) throws ConvertedDocumentToFileException {
        try {
            String encodedAudio = document.getString(AUDIO_FIELD_NAME);
            byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
            File outputFile = File.createTempFile(AUDIO_FIELD_NAME, AUDIO_EXTENSION);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(audioBytes);
            fos.close();
            return outputFile;
        } catch (IOException e) {
            throw new ConvertedDocumentToFileException(e.getMessage(), e);
        }
    }

}
