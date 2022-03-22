package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.QuestionAnswer;
import com.bezkoder.springjwt.models.QuestionOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
