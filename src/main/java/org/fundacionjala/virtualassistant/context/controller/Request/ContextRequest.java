package org.fundacionjala.virtualassistant.context.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
@Valid
@NotEmpty
@AllArgsConstructor()
public class ContextRequest {
    String title;
    Long idUser;
}
