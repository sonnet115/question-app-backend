package com.game.portal.repository;

import com.game.portal.models.QuestionOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsOptionRepository extends JpaRepository<QuestionOptions, Long> {
    void deleteAllById(Long longs);
}
