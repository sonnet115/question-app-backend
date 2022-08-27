package com.game.portal.controllers;

import com.game.portal.models.QuestionSets;
import com.game.portal.models.User;
import com.game.portal.payload.request.QuestionSetRequest;
import com.game.portal.payload.response.MessageResponse;
import com.game.portal.repository.QuestionSetsRepository;
import com.game.portal.repository.UserRepository;
import com.game.portal.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question-set")
public class QuestionSetsController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionSetsRepository questionSetsRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuestionSetRequest questionSetRequest) {
        QuestionSets questionSets = new QuestionSets();
        questionSets.setName(questionSetRequest.getName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            questionSets.setUser(user);
        }
        questionSetsRepository.save(questionSets);
        return ResponseEntity.ok(new MessageResponse(questionSetRequest.getName() + " Created Successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        return ResponseEntity.ok(user.getQuestionSets());

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<QuestionSets> questionSets = questionSetsRepository.findById(id);
        if (questionSets.isPresent()) {
            return ResponseEntity.ok(questionSets.get());
        }
        return ResponseEntity.ok(new MessageResponse("Not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody QuestionSetRequest questionSetRequest) {
        QuestionSets questionSets = questionSetsRepository.getById(id);
        questionSets.setName(questionSetRequest.getName());
        questionSetsRepository.save(questionSets);
        return ResponseEntity.ok(new MessageResponse(questionSetRequest.getName() + " Updated Successfully"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        QuestionSets questionSets = questionSetsRepository.getById(id);
        questionSetsRepository.delete(questionSets);
        return ResponseEntity.ok(new MessageResponse(questionSets.getName() + "  Deleted Successfully"));
    }
}
