package org.fundacionjala.virtualassistant.taskhandler.intents;

import org.fundacionjala.virtualassistant.taskhandler.exception.ConverterException;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import java.util.List;

public class EntityConverter {
    private EntityConverter() throws ConverterException {
        throw new ConverterException(ConverterException.CONVERTER_CLASS);
    }

    public static EntityArgs convert(List<IntentEntity> list) {
        EntityArgs.EntityArgsBuilder entityBuilder = EntityArgs.builder();

        if (!list.isEmpty()) {
            entityBuilder.primaryArg(list.get(0));
        }
        if (list.size() >= 2) {
            entityBuilder.secondaryArg(list.get(1));
        }
        return entityBuilder.build();
    }
}
