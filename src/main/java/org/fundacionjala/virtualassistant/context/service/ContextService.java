package org.fundacionjala.virtualassistant.context.service;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.repository.ContextRepository;
import org.fundacionjala.virtualassistant.models.ContextEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ContextService {

    ContextRepository contextRepository;

    public ContextService(ContextRepository contextRepository){
        this.contextRepository = contextRepository;
    }

    public List<ContextResponse> findContextByUserId(Long idUser) {
        List<ContextEntity> contextEntities = contextRepository.findByIdUser(idUser);;
        return contextEntities.stream()
                .map(ContextResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ContextEntity saveContext(ContextResponse request) {
        if(isNull(request)){
            throw new NullPointerException();
        }

        ContextEntity contextEntity = new ContextEntity(request.getTitle(), request.getIdUser());
        return contextRepository.save(contextEntity);
    }
}
