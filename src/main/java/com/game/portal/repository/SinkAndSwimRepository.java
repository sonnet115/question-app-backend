package com.game.portal.repository;

import com.game.portal.models.SinkAndSwim;
import com.game.portal.models.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinkAndSwimRepository extends DataTablesRepository<SinkAndSwim, Long> {

    List<SinkAndSwim> findAllByUserAndActive(User user, String active);
}
