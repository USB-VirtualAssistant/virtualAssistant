package org.fundacionjala.virtualassistant.context.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor()
public class ContextRequest {
    @NotEmpty(message = "title file must not be empty")
    String title;
    @NotNull(message = "Id user must not be null")
    Long idUser;
}