package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
@Builder
public class IntentEntity {
    String entity;
    String value;
}
