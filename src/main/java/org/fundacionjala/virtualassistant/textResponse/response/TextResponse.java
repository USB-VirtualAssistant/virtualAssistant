package org.fundacionjala.virtualassistant.textResponse.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
