package org.fundacionjala.virtualassistant.mongo.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Document(collection = "recordings")
public class Recording {
    @Id
    private String idRecording;
    private Long idUser;
    private Long idChat;
    private org.bson.Document audioFile;

    public Recording(Long idUser, Long idChat, org.bson.Document metadata) {
        this.idUser = idUser;
        this.idChat = idChat;
        this.audioFile = metadata;
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

    public org.bson.Document getAudioFile() {
        return audioFile;
    }
}