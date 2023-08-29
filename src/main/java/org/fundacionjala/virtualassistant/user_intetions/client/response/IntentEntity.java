package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
public class IntentEntity {
    String entity;
    String value;
}
