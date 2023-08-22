package org.fundacionjala.virtualassistant.textrequest.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.time.ZonedDateTime;

@Value
@Builder
@Jacksonized
public class TextRequest {

    Long idUser;

    String text;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime date;

    Long idContext;

    String idAudioMongo;
}
