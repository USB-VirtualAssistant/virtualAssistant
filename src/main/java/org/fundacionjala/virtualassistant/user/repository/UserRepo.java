package org.fundacionjala.virtualassistant.user.repository;

import org.fundacionjala.virtualassistant.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
