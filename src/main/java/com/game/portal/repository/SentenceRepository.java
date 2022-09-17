package com.game.portal.repository;

import com.game.portal.models.Sentence;
import com.game.portal.models.SinkAndSwim;
import com.game.portal.models.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceRepository extends DataTablesRepository<Sentence, Long> {
    List<Sentence> findAllByUserAndActive(User user, String status);
}
