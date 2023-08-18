package org.fundacionjala.virtualassistant.opennlp;

import lombok.Data;

@Data
public class IntentWrapper {
    private String intent;
    private String[] entities;

    public IntentWrapper(String userRequest) {
        this.intent = DocumentCategorizerExample.getIntent(userRequest);
        this.entities = NameEntityRecognition.recognizeEntity(userRequest);
    }


}
