package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByIdUserAndIdContext(long idUser, long idContext);
}
