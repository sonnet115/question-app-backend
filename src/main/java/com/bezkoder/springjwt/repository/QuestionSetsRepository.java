package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.QuestionSets;
import com.bezkoder.springjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionSetsRepository extends JpaRepository<QuestionSets, Long> {
    List<QuestionSets> findByUser(User user);

    Optional<QuestionSets> findById(Long id);
}
