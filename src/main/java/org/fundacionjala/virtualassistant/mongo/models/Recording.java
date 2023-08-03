package org.fundacionjala.virtualassistant.mongo.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "recordings")
public class Recording {

    @Id
    private String idRecording;
    private Long idUser;
    private Long idChat;
    private org.bson.Document audioFile;

    public Recording(Long idUser, Long idChat, org.bson.Document audioFile) {
        this.idUser = idUser;
        this.idChat = idChat;
        this.audioFile = audioFile;
    }

    public String getIdRecording() {
        return idRecording;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdChat() {
        return idChat;
    }

    public org.bson.Document getAudio() {
        return (org.bson.Document) audioFile;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recording recording = (Recording) o;
        return Objects.equals(idRecording, recording.idRecording)
                && Objects.equals(idUser, recording.idUser) && Objects.equals(idChat, recording.idChat) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecording, idUser, idChat, audioFile);
    }
}
