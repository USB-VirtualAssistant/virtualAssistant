package org.fundacionjala.virtualassistant.context.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor()
public class ContextRequest {
    String title;
    Long idUser;
}
