package org.fundacionjala.virtualassistant.context.controller.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.fundacionjala.virtualassistant.models.ContextEntity;

@Value
@Builder
@AllArgsConstructor
public class ContextResponse {
    private Long idContext;
    private String title;
    private Long idUser;


    public static ContextResponse fromEntity(ContextEntity context){
        return ContextResponse.builder()
                .idUser(context.getIdUser())
                .title(context.getTitle())
                .idContext(context.getIdContext())
                .build();
    }
}
