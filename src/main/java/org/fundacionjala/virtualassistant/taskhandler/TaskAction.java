package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import java.util.List;

public interface TaskAction {
    String handleAction(List<IntentEntity> entities);
}
