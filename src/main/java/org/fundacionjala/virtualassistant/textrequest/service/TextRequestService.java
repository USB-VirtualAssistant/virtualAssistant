package org.fundacionjala.virtualassistant.textrequest.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class TextRequestService {

    private static final String TEXT_REQUEST_USER_ID_NULL = "User id should not be null";

    RequestEntityRepository requestEntityRepository;

    public TextRequestResponse createTextRequest(TextRequest textRequest) throws TextRequestException {
        if (null == textRequest.getIdUser() || textRequest.getIdUser() <= 0) {
            throw new TextRequestException(TEXT_REQUEST_USER_ID_NULL);
        }

        RequestEntity requestEntity = requestEntityFromTextRequest(textRequest);
        RequestEntity savedRequestEntity = requestEntityRepository.save(requestEntity);


        return TextRequestResponse.builder()
                .idUser(savedRequestEntity.getIdUser())
                .text(savedRequestEntity.getText())
                .idContext(savedRequestEntity.getIdContext())
                .build();
    }

    public TextRequest save(long idRequest, String text, Long idAudio, Long idUser) {
        RequestEntity requestEntity = RequestEntity.builder()
                .idRequest(idRequest)
                .text(text)
                .date(ZonedDateTime.now())
                .idAudioMongo(idAudio)
                .idUser(idUser)
                .build();
        RequestEntity requestEntitySaved = requestEntityRepository.save(requestEntity);
        return TextRequest.builder()
                .idUser(requestEntitySaved.getIdUser())
                .idAudioMongo(requestEntitySaved.getIdAudioMongo())
                .idContext(requestEntitySaved.getIdContext())
                .text(requestEntitySaved.getText()).build();
    }

    private RequestEntity requestEntityFromTextRequest(TextRequest textRequest) {
        return RequestEntity.builder()
                .idAudioMongo(textRequest.getIdAudioMongo())
                .date(textRequest.getDate())
                .idContext(textRequest.getIdContext())
                .idUser(textRequest.getIdUser())
                .text(textRequest.getText())
                .build();
    }
}
