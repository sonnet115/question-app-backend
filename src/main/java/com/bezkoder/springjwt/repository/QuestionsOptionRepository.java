package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.QuestionOptions;
import com.bezkoder.springjwt.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsOptionRepository extends JpaRepository<QuestionOptions, Long> {
}
