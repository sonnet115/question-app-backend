package com.game.portal.repository;

import com.game.portal.models.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
