package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.Getter;
import java.util.List;

@Getter
public class IntentResponse {
     List<IntentEntity> intentEntities;
     Intent intent;
}
