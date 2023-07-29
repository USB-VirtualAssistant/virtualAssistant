package org.fundacionjala.virtualassistant.mongoDB.model;


import com.mongodb.client.gridfs.model.GridFSFile;

import java.io.File;
import java.util.Objects;

public class Recording {

    private Long idRecording;
    private Long idUser;
    private Long idChat;
    private GridFSFile audio;


    public Recording(Long idRecording, Long idUser, Long idChat, GridFSFile audio) {
        this.idRecording = idRecording;
        this.idUser = idUser;
        this.idChat = idChat;

    }

    public Long getIdRecording() {
        return idRecording;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdChat() {
        return idChat;
    }

    public GridFSFile getAudio() {
        return audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recording recording = (Recording) o;
        return Objects.equals(idRecording, recording.idRecording) && Objects.equals(idUser, recording.idUser) && Objects.equals(idChat, recording.idChat) && Objects.equals(audio, recording.audio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecording, idUser, idChat, audio);
    }
}
