package org.fundacionjala.virtualassistant.textrequest.controller.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TextRequestResponse {
  Long idUser;
  String text;
}
