package org.fundacionjala.virtualassistant.mongoDB.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Document(collection = "recordings")
public class Recording {

    @Id
    private String idRecording;
    private Long idUser;
    private Long idChat;
    private MultipartFile audioFile;


    public Recording( Long idUser, Long idChat, MultipartFile audioFile) {
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

    public MultipartFile getAudio() {
        return audioFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recording recording = (Recording) o;
        return Objects.equals(idRecording, recording.idRecording) && Objects.equals(idUser, recording.idUser) && Objects.equals(idChat, recording.idChat) && Objects.equals(audioFile, recording.audioFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecording, idUser, idChat, audioFile);
    }
}
