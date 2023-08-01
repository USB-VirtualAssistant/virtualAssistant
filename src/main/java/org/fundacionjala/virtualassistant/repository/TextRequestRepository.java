package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextRequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByUserIdAndContextId(Long id, Long contextId);
}
