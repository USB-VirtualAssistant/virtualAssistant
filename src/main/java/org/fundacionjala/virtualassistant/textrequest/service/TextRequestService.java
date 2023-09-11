package org.fundacionjala.virtualassistant.textrequest.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.models.RequestEntity;

import javax.validation.constraints.NotNull;

import org.fundacionjala.virtualassistant.clients.openai.component.OpenAIComponent;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class TextRequestService {
    private static final String TEXT_REQUEST_USER_ID_NULL = "User id should not be null";
    private RequestEntityRepository requestEntityRepository;
    private OpenAIComponent openAi;
    private TextResponseService responseService;

    public TextRequestResponse createTextRequest(@NotNull TextRequest textRequest) throws TextRequestException {
        if (isNull(textRequest)) {
            throw new TextRequestException(TEXT_REQUEST_USER_ID_NULL);
        }

        RequestEntity requestEntity = TextRequestParser.parseFrom(textRequest);
        RequestEntity savedRequestEntity = requestEntityRepository.save(requestEntity);
        TextResponse textResponse = responseService.save(ParameterResponse.builder()
                .text(openAi.getResponse(savedRequestEntity.getText()))
                .request(TextRequestResponse.builder()
                        .idRequest(savedRequestEntity.getIdRequest())
                        .build())
                .build());

        return TextRequestParser.parseFrom(savedRequestEntity, textResponse);
    }

    public TextRequest save(long idRequest, String text, Long idAudio, Long idUser) {
        RequestEntity requestEntity = RequestEntity.builder()
                .idRequest(idRequest)
                .text(text)
                .date(ZonedDateTime.now())
                .idAudioMongo(idAudio.toString())
                .idUser(idUser)
                .build();
        RequestEntity requestEntitySaved = requestEntityRepository.save(requestEntity);
        return TextRequest.builder()
                .idUser(requestEntitySaved.getIdUser())
                .idAudioMongo(requestEntitySaved.getIdAudioMongo())
                .context(ContextParser.parseFrom(requestEntitySaved.getContextEntity()))
                .text(requestEntitySaved.getText()).build();
    }

    public List<TextRequestResponse> getTextRequestByUserAndContext(Long id, Long contextId) {
        return requestEntityRepository.findAllByIdUserAndContextEntityIdContext(id, contextId).stream()
                .map(TextRequestParser::parseFrom)
                .collect(Collectors.toList());
    }
}