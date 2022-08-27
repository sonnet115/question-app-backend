package com.game.portal.repository;

import com.game.portal.models.SinkAndSwim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinkAndSwimRepository extends JpaRepository<SinkAndSwim, Long> {
}
