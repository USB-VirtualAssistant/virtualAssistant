package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Intent {
    double confidence;
    String name;
}
