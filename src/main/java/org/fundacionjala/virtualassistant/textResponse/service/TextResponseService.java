package org.fundacionjala.virtualassistant.textResponse.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.textResponse.repository.ResponseEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import java.util.List;

@Service
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
        return parseEntity(responseEntitySaved);
    }

    public List<TextResponse> getAll() {
        List<TextResponse> textResponses = repository.findAll()
                .stream()
                .map(this::parseEntity)
                .collect(Collectors.toList());

        return textResponses;
    }

    private TextResponse parseEntity(ResponseEntity entity) {
        return TextResponse.builder()
                .idResponse(entity.getIdResponse())
                .idRequest(entity.getIdRequest())
                .text(entity.getText())
                .date(entity.getDate())
                .build();
    }
}
