package org.fundacionjala.virtualassistant.mongo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
public class RecordingRequest {
    String idRecording;
    Long idUser;
    Long idChat;
    File audioFile;
}