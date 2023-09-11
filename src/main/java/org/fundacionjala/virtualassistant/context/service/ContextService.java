package org.fundacionjala.virtualassistant.context.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.exception.ContextNotFoundException;
import org.fundacionjala.virtualassistant.context.exception.ContextRequestException;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.context.repository.ContextRepository;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
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
    private UserRepo userRepo;

    public List<ContextResponse> findContextByUserId(Long idUser) throws ContextException {
        if(isUserNull(idUser)){
            throw new ContextNotFoundException(ContextRequestException.MESSAGE_INVALID_ID);
        }

        List<ContextEntity> contextEntities = contextRepository.findByUserEntityIdUser(idUser);
        return convertListContextToResponse(contextEntities);
    }

    public ContextResponse saveContext(ContextRequest request) throws ContextException {
        verifyContextRequest(request);
        ContextEntity contextEntity = ContextParser.parseFrom(request);
        return ContextParser.parseFrom(contextRepository.save(contextEntity));
    }

    public Optional<ContextResponse> findById(Long idContext) throws ContextRequestException {
        if(contextRepository.findById(idContext).isEmpty()){
            throw new ContextRequestException(ContextRequestException.MESSAGE_INVALID_ID);
        }

        Optional<ContextEntity> optionalContext = contextRepository.findById(idContext);
        return optionalContext.map(ContextParser::parseFrom);
    }

    public ContextResponse editContext(Long idContext, ContextRequest request) throws ContextException {
        verifyContextRequest(request);
        if (contextRepository.findById(idContext).isEmpty()) {
            throw new ContextRequestException(ContextRequestException.MESSAGE_INVALID_ID);
        }

        ContextEntity contextEntity = ContextParser.parseFrom(idContext,request);
        return ContextResponse.fromEntity(contextRepository.save(contextEntity));
    }

    public boolean deleteContext(Long idContext) throws ContextException {
        try {
            contextRepository.deleteById(idContext);
            return true;
        }catch (Exception e){
            throw new ContextRequestException(ContextException.MESSAGE_DELETE_ERROR);
        }
    }

    private List<ContextResponse> convertListContextToResponse(List<ContextEntity> entityList)
            throws ContextException {
        if (isNull(processorEither)) {
            throw new ContextException(ContextException.MESSAGE_CONTEXT_NULL);
        }

        return getContextResponses(entityList);
    }

    private boolean isUserNull(Long idUser){
        Optional<UserEntity> user = userRepo.findByIdUser(idUser);
        return user.isEmpty();
    }

    private void verifyContextRequest(ContextRequest request) throws ContextException {
        if (isNull(request)) {
            throw new ContextException(ContextException.MESSAGE_CONTEXT_REQUEST_NULL);
        }

        if (isUserNull(request.getUserRequest().getIdUser())){
            throw new ContextRequestException(ContextRequestException.MESSAGE_CONTEXT_ID_USER_DONT_EXIST);
        }
    }
}