package org.fundacionjala.virtualassistant.user_intetions.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IntentResponse {
     List<IntentEntity> intentEntities;
     Intent intent;
}
