package org.fundacionjala.virtualassistant.poc_user_intentions.model;
import lombok.Data;

@Data
public class WITEntity {
    private String currentEntityType;
    private String actValue;

    public WITEntity(String currentEntityType, String actValue) {
        this.currentEntityType = currentEntityType;
        this.actValue = actValue;
    }

}
