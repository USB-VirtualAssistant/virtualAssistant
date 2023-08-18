package org.fundacionjala.virtualassistant.textResponse.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.repository.ResponseEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import java.time.ZonedDateTime;

@AllArgsConstructor
public class TextResponseService {

    private ResponseEntityRepository repository;

    public TextResponse save(long idRequest, String text) {
        ResponseEntity responseEntity = ResponseEntity.builder()
                .idRequest(idRequest)
                .text(text)
                .date(ZonedDateTime.now())
                .build();
        ResponseEntity responseEntitySaved = repository.save(responseEntity);
        return TextResponse.builder()
                .idResponse(responseEntitySaved.getIdResponse())
                .idRequest(responseEntitySaved.getIdRequest())
                .text(responseEntitySaved.getText())
                .date(responseEntitySaved.getDate())
                .build();
    }
}