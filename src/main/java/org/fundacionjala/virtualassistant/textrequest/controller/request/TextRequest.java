package org.fundacionjala.virtualassistant.textrequest.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Date;

@Value
@Builder
public class TextRequest {

    Long idUser;

    String text;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date;

    long idAudioMongo;
}
