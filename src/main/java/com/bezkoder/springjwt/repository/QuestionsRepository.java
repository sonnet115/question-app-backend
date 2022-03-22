package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
