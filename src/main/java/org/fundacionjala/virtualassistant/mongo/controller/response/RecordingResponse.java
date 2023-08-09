package org.fundacionjala.virtualassistant.mongo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import java.io.File;

@Builder
@Value
@AllArgsConstructor
public class RecordingResponse {
    String idRecording;
    Long idUser;
    Long idChat;
    File audioFile;
}
