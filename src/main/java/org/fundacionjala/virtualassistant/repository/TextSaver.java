package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;


public interface TextSaver {
    RequestEntity saveText(String text, int idAudioMongo);
}
