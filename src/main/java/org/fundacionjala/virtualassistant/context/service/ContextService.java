package org.fundacionjala.virtualassistant.context.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.repository.ContextRepository;
import org.fundacionjala.virtualassistant.models.ContextEntity;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ContextService {

    private ContextRepository contextRepository;
    private ProcessorEither<Exception, ContextResponse> processorEither;

    public List<ContextResponse> findContextByUserId(Long idUser) throws ContextException {
        List<ContextEntity> contextEntities = contextRepository.findByIdUser(idUser);
        return convertListContextToResponse(contextEntities);
    }

    public ContextResponse saveContext(ContextRequest request) throws ContextException {
        if(isNull(request)){
            throw new ContextException(ContextException.MESSAGE_CONTEXT_REQUEST_NULL);
        }

        ContextEntity contextEntity = new ContextEntity(request.getTitle(), request.getIdUser());
        return ContextResponse.fromEntity(contextRepository.save(contextEntity));
    }

    private List<ContextResponse> convertListContextToResponse(List<ContextEntity> entityList)
            throws ContextException {
        if (isNull(processorEither)){
            throw new ContextException(ContextException.MESSAGE_CONTEXT_NULL);
        }

        return entityList.stream()
                .map(processorEither.lift(context -> {
                    try{
                        return Either.right(ContextResponse.fromEntity(context));
                    }catch (ContextException exception) {
                        return Either.left(exception);
                    }
                })).filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }
}
