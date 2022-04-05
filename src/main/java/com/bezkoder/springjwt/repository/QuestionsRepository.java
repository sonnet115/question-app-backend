package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.QuestionSets;
import com.bezkoder.springjwt.models.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends DataTablesRepository<Questions, Long> {
    Page<Questions> findAllByQuestionSets(QuestionSets questionSets, Pageable pageable);
}
