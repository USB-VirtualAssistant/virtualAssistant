package org.fundacionjala.virtualassistant.textrequest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class TextRequestResponse {
  @NotNull
  Long idRequest;
  Long idUser;
  Long idContext;
  String text;
  ZonedDateTime date;
}
