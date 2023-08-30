package org.fundacionjala.virtualassistant.conversation.service;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.fundacionjala.virtualassistant.conversation.repository.ConversationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationEntityRepository repository;

    @Autowired
    public ConversationService(ConversationEntityRepository repository) {
        this.repository = repository;
    }

    public List<ConversationEntity> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageable).getContent();
    }
}
