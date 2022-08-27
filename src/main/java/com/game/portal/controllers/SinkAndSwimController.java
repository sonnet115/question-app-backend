package com.game.portal.controllers;

import com.game.portal.models.SinkAndSwim;
import com.game.portal.models.User;
import com.game.portal.payload.request.SinkAndSwimRequest;
import com.game.portal.payload.response.MessageResponse;
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

import java.rmi.server.UID;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sink-swim")
public class SinkAndSwimController {

    @Autowired
    SinkAndSwimRepository sinkAndSwimRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilesStorageService filesStorageService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute SinkAndSwimRequest sinkAndSwimRequest) {
        try {
            MultipartFile image = sinkAndSwimRequest.getImage();
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            if (filesStorageService.save(image, imageName) > 0) {
                SinkAndSwim sinkAndSwim = new SinkAndSwim();
                sinkAndSwim.setImage(imageName);
                sinkAndSwim.setAnswer(sinkAndSwimRequest.getAnswer());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

                Optional<User> optionalUser = userRepository.findById(userDetails.getId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    sinkAndSwim.setUser(user);
                }
                sinkAndSwimRepository.save(sinkAndSwim);

                return ResponseEntity.ok(new MessageResponse("Question Created Successfully"));
            }

            return ResponseEntity.ok(new MessageResponse("Question Creation Failed"));

        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Question Creation Failed"));
        }
    }

    @PutMapping("/update/{qid}")
    public ResponseEntity<?> update(@PathVariable Long qid, @RequestBody SinkAndSwimRequest sinkAndSwimRequest) {
        try {
            Optional<SinkAndSwim> sinkAndSwimOptional = sinkAndSwimRepository.findById(qid);

            SinkAndSwim sinkAndSwim = sinkAndSwimOptional.get();
            sinkAndSwim.setAnswer(sinkAndSwim.getAnswer());
            sinkAndSwim.setImage(sinkAndSwim.getImage());
            sinkAndSwimRepository.save(sinkAndSwim);

            return ResponseEntity.ok(new MessageResponse("Question Updated Successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Question Creation Failed"));
        }
    }


}
