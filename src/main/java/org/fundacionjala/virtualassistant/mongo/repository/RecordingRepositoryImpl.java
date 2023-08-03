package org.fundacionjala.virtualassistant.mongo.repository;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordingRepositoryImpl implements RecordingRepo {

    MongoTemplate mongoTemplate;
    private static final String AUDIO_FIELD_NAME = "audio";

    @Override
    public List<Recording> getAllRecordings() {
        return mongoTemplate.findAll(Recording.class);
    }

    @Override
    public Recording getRecording(String idRecording) {
        Query query = generateQueryCriteria(idRecording);
        return mongoTemplate.findOne(query, Recording.class);
    }

    @Override
    public Recording getRecordingToUser(String idRecording, Long idUser, Long idChat) {
        Query query = new Query(Criteria.where("idUser").is(idUser).and("idChat").is(idChat).and("idRecording").is(idRecording));
        return mongoTemplate.findOne(query, Recording.class);
    }

    @Override
    public List<Recording> getAllRecordingsToUser(Long idUser, Long idChat) {
        Query query = new Query(Criteria.where("idUser").is(idUser).and("idChat").is(idChat));
        return mongoTemplate.find(query, Recording.class);
    }

    @Override
    public boolean deleteRecording(String idRecording) {
        Query query = generateQueryCriteria(idRecording);
        Recording recordingToDelete = mongoTemplate.findOne(query, Recording.class);
        Optional.ofNullable(recordingToDelete).ifPresent(recording -> {
            mongoTemplate.remove(query, Recording.class);
        });
        return recordingToDelete != null;
    }

    @Override
    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile) {
        Document metadata = generateDocumentRecording(audioFile);
        Recording recording = new Recording(idUser, idChat, metadata);
        return mongoTemplate.save(recording);
    }

    private Document generateDocumentRecording(MultipartFile file) {
        try {
            byte[] audioBytes = file.getBytes();
            String encodedAudio = Base64.getEncoder().encodeToString(audioBytes);
            return new Document(AUDIO_FIELD_NAME, encodedAudio);
        } catch (IOException e) {
            throw new RuntimeException("Error in generateDocumentRecording: Unable to read file data.", e);
        }
    }

    private Query generateQueryCriteria(String idRecording){
        return new Query(Criteria.where("idRecording").is(idRecording));
    }
}
