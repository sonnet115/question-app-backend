package com.game.portal.repository;

import com.game.portal.models.QuestionSets;
import com.game.portal.models.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    Page<Questions> findAllByQuestionSets(QuestionSets questionSets, Pageable pageable);
}
