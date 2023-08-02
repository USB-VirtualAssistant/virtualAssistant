package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


public class TextSaverImpl implements TextSaver {
    private RequestEntityRepository repository;

    public TextSaverImpl(RequestEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestEntity saveText(String text, int idAudioMongo) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setText(text);
        requestEntity.setDate(new Timestamp(System.currentTimeMillis()));
        requestEntity.setIdAudioMongo(idAudioMongo);
        return repository.save(requestEntity);
    }
}
