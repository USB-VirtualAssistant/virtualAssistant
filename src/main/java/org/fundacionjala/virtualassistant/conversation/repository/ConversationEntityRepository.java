package org.fundacionjala.virtualassistant.conversation.repository;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationEntityRepository extends JpaRepository<ConversationEntity, Long> {
}
