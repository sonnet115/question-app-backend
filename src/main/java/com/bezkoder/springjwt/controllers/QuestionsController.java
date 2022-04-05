package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.PaginationRequest;
import com.bezkoder.springjwt.payload.request.QuestionRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionsController {

    @Autowired
    QuestionSetsRepository questionSetsRepository;

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    QuestionsOptionRepository questionsOptionRepository;

    @Autowired
    QuestionsAnswerRepository questionsAnswerRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuestionRequest questionRequest) {
        try {
            Optional<QuestionSets> questionSets = questionSetsRepository.findById(questionRequest.getQueSetID());

            Questions question = new Questions();
            question.setQuestionText(questionRequest.getQuestionText());
            question.setType(questionRequest.getType());
            question.setImagePath(questionRequest.getImage());
            question.setAudioPath(questionRequest.getAudio());
            question.setQuestionSets(questionSets.get());
            questionsRepository.save(question);

            for (QuestionOptions qo : questionRequest.getQuestionOptions()) {
                QuestionOptions questionOptions = new QuestionOptions();
                questionOptions.setQuestions(question);
                questionOptions.setOptionName(qo.getOptionName());
                questionOptions.setOptionValue(qo.getOptionValue());
                questionsOptionRepository.save(questionOptions);
            }

            for (String answer : questionRequest.getAnswers()) {
                QuestionAnswer questionAnswer = new QuestionAnswer();
                questionAnswer.setAnswer(answer);
                questionAnswer.setQuestions(question);
                questionsAnswerRepository.save(questionAnswer);
            }
            return ResponseEntity.ok(new MessageResponse("Question Created Successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Question Creation Failed"));
        }
    }

    @PostMapping("/get/{ques_set_id}")
    public ResponseEntity<?> getByQuestionSet(@PathVariable Long ques_set_id, @RequestBody PaginationRequest paginationRequest) {

        Optional<QuestionSets> optionalQuestionSets = questionSetsRepository.findById(ques_set_id);
        QuestionSets questionSets = optionalQuestionSets.orElse(null);

        PageRequest page = PageRequest.of(paginationRequest.getOffset(), paginationRequest.getLimit());
        Page<Questions> questionsPage = questionsRepository.findAllByQuestionSets(questionSets, page);

        List<Questions> questionsList = questionsPage.stream().collect(Collectors.toCollection(ArrayList::new));
        HashMap<String, Object> response = new HashMap<>();
        response.put("questions", questionsList);
        response.put("totalRecords", questionsPage.getTotalElements());
        response.put("totalPages", questionsPage.getTotalPages());
        response.put("limit", paginationRequest.getLimit());
        response.put("offset", paginationRequest.getOffset());
        return ResponseEntity.ok(response);
    }
}
