package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;

import java.util.List;

public class TextRequestServiceImpl implements TextRequestService{
    RequestEntityRepository requestEntityRepository;

    @Override
    public List<RequestEntity> getTextRequestByUserAndContext(Long id, Long contextId) {
        return requestEntityRepository.findAllByIdUserAndIdContext(id, contextId);
    }
}
