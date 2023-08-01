package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Long> {
}
