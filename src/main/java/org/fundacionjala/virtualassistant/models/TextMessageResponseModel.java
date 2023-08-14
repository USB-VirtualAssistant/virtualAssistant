package org.fundacionjala.virtualassistant.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageResponseModel {
    private List<OutcomeCandidate> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OutcomeCandidate {
        private int index;
        private TextMessageDataModel message;
    }

}