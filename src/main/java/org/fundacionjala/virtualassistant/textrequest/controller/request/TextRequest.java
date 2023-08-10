package org.fundacionjala.virtualassistant.textrequest.controller.request;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Date;

@Value
@Builder
public class TextRequest {

    Long idUser;

    String text;

    Date date;

    long idAudioMongo;
}
