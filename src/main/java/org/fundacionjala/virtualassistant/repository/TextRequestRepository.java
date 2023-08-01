package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRequestRepository extends JpaRepository<RequestEntity, Long> {
}
