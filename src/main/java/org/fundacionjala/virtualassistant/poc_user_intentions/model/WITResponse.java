package org.fundacionjala.virtualassistant.poc_user_intentions.model;

import lombok.Data;

@Data
public class WITResponse {

    private String content;
    private WITEntity[] storedEntities;
    private String actRequestId;

    public WITResponse(String contentText, String actRequestId, WITEntity[] storedEntities) {
        this.actRequestId = actRequestId;
        this.content = contentText;
        this.storedEntities = storedEntities;
    }
}
