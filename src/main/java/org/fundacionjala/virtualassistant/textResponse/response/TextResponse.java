package org.fundacionjala.virtualassistant.textResponse.response;

import lombok.Value;
import lombok.Builder;
import lombok.AllArgsConstructor;
import java.time.ZonedDateTime;

@Value
@Builder
@AllArgsConstructor
public class TextResponse {
    Long idResponse;
    Long idRequest;
    String text;
    ZonedDateTime date;
}
