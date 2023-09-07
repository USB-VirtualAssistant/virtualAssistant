package org.fundacionjala.virtualassistant.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotEmpty
    Long idUser;
    String idGoogle;
}
