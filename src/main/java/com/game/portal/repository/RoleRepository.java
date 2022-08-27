package com.game.portal.repository;

import java.util.Optional;

import com.game.portal.models.ERole;
import com.game.portal.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
