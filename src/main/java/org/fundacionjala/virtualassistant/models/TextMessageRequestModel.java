package org.fundacionjala.virtualassistant.models;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextMessageRequestModel {

    private String model;
    private List<TextMessageDataModel> messages;

    public TextMessageRequestModel(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        addMessage("user",prompt);
    }
    public void addMessage(String role, String content) {
        this.messages.add(new TextMessageDataModel(role, content));
    }
}