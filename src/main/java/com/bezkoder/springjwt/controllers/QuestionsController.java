package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.QuestionRequest;
import com.bezkoder.springjwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        return ResponseEntity.ok("Question Created Successfully");
    }

    @GetMapping("/get/{ques_set_id}")
    public ResponseEntity<?> getByQuestionSet(@PathVariable Long ques_set_id) {
        Optional<QuestionSets> optionalQuestionSets = questionSetsRepository.findById(ques_set_id);
        QuestionSets questionSets = optionalQuestionSets.isPresent() ? optionalQuestionSets.get() : null;

        return ResponseEntity.ok(questionSets.getQuestions());
    }
}
