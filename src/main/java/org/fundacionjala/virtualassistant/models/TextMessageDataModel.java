package org.fundacionjala.virtualassistant.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageDataModel {
    private String role;
    private String content;
}