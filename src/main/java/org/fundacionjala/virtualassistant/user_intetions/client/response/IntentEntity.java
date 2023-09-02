package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
@Getter
public class IntentEntity {
    String entity;
    String value;
}
