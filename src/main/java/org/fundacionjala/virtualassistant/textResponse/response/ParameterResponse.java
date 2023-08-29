package org.fundacionjala.virtualassistant.textResponse.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ParameterResponse {
    @Getter
    Long idRequest;

    @Getter
    String text;
}
