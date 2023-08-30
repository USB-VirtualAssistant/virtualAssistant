package org.fundacionjala.virtualassistant.textResponse.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class ParameterResponse {
    @NotNull
    Long idRequest;

    @NotEmpty
    String text;
}
