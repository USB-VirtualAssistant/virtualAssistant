package org.fundacionjala.virtualassistant.conversation.model;

import lombok.Builder;
import lombok.Value;
import java.time.ZonedDateTime;

@Value
@Builder
public class ConversationResponse {
    Long idRequest;
    String textRequest;
    ZonedDateTime dateRequest;
    String idAudio;
    String textResponse;
    ZonedDateTime dateResponse;
}
