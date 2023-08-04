package org.fundacionjala.virtualassistant.mongo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class RecordingRequest {
    Long idUser;
    Long idChat;
    MultipartFile audioFile;
}