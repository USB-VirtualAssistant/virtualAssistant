package org.fundacionjala.virtualassistant.mongoDB.repository;

import org.fundacionjala.virtualassistant.mongoDB.model.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public class RecordingRepositoryImpl implements RecordingRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Recording getRecording(Long idRecording) {
        Query query = new Query(Criteria.where("idRecording").is(idRecording));
        return mongoTemplate.findOne(query, Recording.class);
    }

    @Override
    public boolean deleteRecording(Long idRecording) {
        Query query = new Query(Criteria.where("idRecording").is(idRecording));
        Recording recordingToDelete = mongoTemplate.findOne(query, Recording.class);
        if (recordingToDelete != null) {
            mongoTemplate.remove(query, Recording.class);
            return true;
        }
        return false;
    }

    @Override
    public List<Recording> getAllRecordingsToUser(Long idUser, Long idChat) {
        Query query = new Query(Criteria.where("idUser").is(idUser).and("idChat").is(idChat));
        return mongoTemplate.find(query, Recording.class);
    }

    @Override
    public List<Recording> getAllRecordings() {
        return mongoTemplate.findAll(Recording.class);
    }

    @Override
    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioPath) {
        Recording recording = new Recording(idUser, idChat, audioPath);
        return mongoTemplate.save(recording);
    }
}
