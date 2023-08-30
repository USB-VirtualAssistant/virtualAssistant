package org.fundacionjala.virtualassistant.conversation.controller.response;

import lombok.Builder;
import lombok.Value;
import java.time.ZonedDateTime;

@Value
@Builder
public class ConversationResponse {
    Long idRequest;
    String textRequest;
    ZonedDateTime dateRequest;
    Long idAudio;
    Long idUser;
    Long idContext;
    Long textResponse;
    ZonedDateTime dateResponse;
}
