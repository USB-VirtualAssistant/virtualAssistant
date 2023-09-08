package org.fundacionjala.virtualassistant.context.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.context.repository.ContextRepository;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.fundacionjala.virtualassistant.user.controller.parser.UserParser.getContextResponses;

@Service
@AllArgsConstructor
public class ContextService {

    private ContextRepository contextRepository;
    private ProcessorEither<Exception, ContextResponse> processorEither;

    public List<ContextResponse> findContextByUserId(Long idUser) throws ContextException {
        List<ContextEntity> contextEntities = contextRepository.findByUserEntity_IdUser(idUser);
        return convertListContextToResponse(contextEntities);
    }

    public ContextResponse saveContext(ContextRequest request) throws ContextException {
        if (isNull(request)) {
            throw new ContextException(ContextException.MESSAGE_CONTEXT_REQUEST_NULL);
        }

        ContextEntity contextEntity = ContextParser.parseFrom(request);
        return ContextParser.parseFrom(contextRepository.save(contextEntity));
    }

    public Optional<ContextResponse> findById(Long idContext) {
        Optional<ContextEntity> optionalContext = contextRepository.findById(idContext);
        return optionalContext.map(ContextParser::parseFrom);
    }

    private List<ContextResponse> convertListContextToResponse(List<ContextEntity> entityList)
            throws ContextException {
        if (isNull(processorEither)) {
            throw new ContextException(ContextException.MESSAGE_CONTEXT_NULL);
        }

        return getContextResponses(entityList);
    }
}