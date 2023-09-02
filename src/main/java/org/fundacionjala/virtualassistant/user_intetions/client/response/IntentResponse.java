package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class IntentResponse {
     List<IntentEntity> intentEntities;
     Intent intent;
}
