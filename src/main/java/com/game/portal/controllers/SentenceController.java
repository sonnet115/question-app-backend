package com.game.portal.controllers;

import com.game.portal.models.Sentence;
import com.game.portal.models.SinkAndSwim;
import com.game.portal.models.User;
import com.game.portal.payload.request.SentenceRequest;
import com.game.portal.payload.request.SinkAndSwimRequest;
import com.game.portal.payload.response.MessageResponse;
import com.game.portal.repository.SentenceRepository;
import com.game.portal.repository.SinkAndSwimRepository;
import com.game.portal.repository.UserRepository;
import com.game.portal.security.services.UserDetailsImpl;
import com.game.portal.services.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            if(sentenceRequest.getSentence().length() > 50){
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
            sentenceRepository.save(sentence);
            return ResponseEntity.ok(new MessageResponse("Sentence Creation Successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Sentence Creation Failed"));
        }
    }


    @GetMapping("/get")
    public ResponseEntity<?> getByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        return ResponseEntity.ok(sentenceRepository.findAllByUser(user));
    }

}
