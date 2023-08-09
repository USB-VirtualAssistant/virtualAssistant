package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;

import java.sql.Timestamp;


public class TextSaverImpl implements TextSaver {
    private RequestEntityRepository repository;

    public TextSaverImpl(RequestEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestEntity saveText(String text, int idAudioMongo) {
        Timestamp data = new Timestamp(System.currentTimeMillis());
        RequestEntity requestEntity = new RequestEntity(text,data,idAudioMongo);
        requestEntity.setText(text);
        requestEntity.setDate(data);
        requestEntity.setIdAudioMongo(idAudioMongo);
        return repository.save(requestEntity);
    }
}
