package com.game.portal.controllers;

import com.game.portal.models.Sentence;
import com.game.portal.models.User;
import com.game.portal.payload.request.SentenceRequest;
import com.game.portal.payload.response.MessageResponse;
import com.game.portal.repository.SentenceRepository;
import com.game.portal.repository.UserRepository;
import com.game.portal.security.services.UserDetailsImpl;
import com.game.portal.specifications.SearchCriteria;
import com.game.portal.specifications.sentences.GetByUserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sentence")
public class SentenceController {

    @Autowired
    SentenceRepository sentenceRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SentenceRequest sentenceRequest) {
        try {
            Sentence sentence = new Sentence();
            if (sentenceRequest.getSentence().length() > 75) {
                return ResponseEntity.ok(new MessageResponse("Max length is 50 Character"));
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                sentence.setUser(user);
            }
            sentence.setSentence(sentenceRequest.getSentence());
            sentence.setActive(sentenceRequest.getActive());
            sentenceRepository.save(sentence);
            return ResponseEntity.ok(new MessageResponse("Sentence Creation Successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Sentence Creation Failed"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody SentenceRequest sentenceRequest, @PathVariable Long id) {
        try {
            Sentence sentence = sentenceRepository.findById(id).get();
            if (sentenceRequest.getSentence().length() > 75) {
                return ResponseEntity.ok(new MessageResponse("Max length is 50 Character"));
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                sentence.setUser(user);
            }
            sentence.setSentence(sentenceRequest.getSentence());
            sentence.setActive(sentenceRequest.getActive());
            sentenceRepository.save(sentence);
            return ResponseEntity.ok(new MessageResponse("Sentence Updated Successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Sentence Update Failed"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        return ResponseEntity.ok(sentenceRepository.findAllByUserAndActive(user, "1"));
    }

    @PostMapping("/get")
    public DataTablesOutput<?> getByUser(@RequestBody DataTablesInput input) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        GetByUserSpecification byUser = new GetByUserSpecification(new SearchCriteria("user", "=", user.getId()));
        return sentenceRepository.findAll(input, byUser);
    }

    @GetMapping("/get/by/{id}")
    public ResponseEntity<?> getByID(@PathVariable Long id) {
        return ResponseEntity.ok(sentenceRepository.findById(id));
    }

}
