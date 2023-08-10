package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TextSaverImpl implements TextSaver {

    private RequestEntityRepository repository;

    public TextSaverImpl(RequestEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestEntity saveText(String text, int idAudioMongo) {
        LocalDate localDate = LocalDate.now();
        Date date= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        RequestEntity requestEntity = new RequestEntity(text,date,idAudioMongo);
        return repository.save(requestEntity);
    }
}
