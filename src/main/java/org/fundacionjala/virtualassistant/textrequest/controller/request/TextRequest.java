package org.fundacionjala.virtualassistant.textrequest.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Value
@Builder
@Jacksonized
public class TextRequest {
    @NotNull
    Long idUser;
    String text;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime date;
    @Valid
    ContextResponse context;
    @NotNull
    String idAudioMongo;
}