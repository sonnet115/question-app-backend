package com.game.portal.controllers;

import com.game.portal.models.SinkAndSwim;
import com.game.portal.models.User;
import com.game.portal.payload.request.SinkAndSwimRequest;
import com.game.portal.payload.response.MessageResponse;
import com.game.portal.repository.SinkAndSwimRepository;
import com.game.portal.repository.UserRepository;
import com.game.portal.security.services.UserDetailsImpl;
import com.game.portal.services.FilesStorageService;
import com.game.portal.specifications.SearchCriteria;
import com.game.portal.specifications.sinkswim.GetByUserSpecification;
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
@RequestMapping("/api/sink-swim")
public class SinkAndSwimController {

    @Autowired
    SinkAndSwimRepository sinkAndSwimRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilesStorageService filesStorageService;

    /*@PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute SinkAndSwimRequest sinkAndSwimRequest) {
        try {
            MultipartFile image = sinkAndSwimRequest.getImage();
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            if (filesStorageService.save(image, imageName) >0) {
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

            return ResponseEntity.ok(new MessageResponse("Question Creation Failed "));

        } catch (Exception e) {
            System.err.println("try "+ e.getMessage());
            return ResponseEntity.ok(new MessageResponse("Question Creation Failed try"));
        }
    }*/

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SinkAndSwimRequest sinkAndSwimRequest) {
        try {

            SinkAndSwim sinkAndSwim = new SinkAndSwim();
            sinkAndSwim.setImage(sinkAndSwimRequest.getImage());
            sinkAndSwim.setAnswer(sinkAndSwimRequest.getAnswer());
            sinkAndSwim.setName(sinkAndSwimRequest.getName());
            sinkAndSwim.setActive("1");

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                sinkAndSwim.setUser(user);
            }
            sinkAndSwimRepository.save(sinkAndSwim);

            return ResponseEntity.ok(new MessageResponse("Object Creation Successfully"));
        } catch (Exception e) {
            System.err.println("try " + e.getMessage());
            return ResponseEntity.ok(new MessageResponse("Failed to Create Object"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SinkAndSwimRequest sinkAndSwimRequest) {
        try {
            SinkAndSwim sinkAndSwim = sinkAndSwimRepository.findById(id).get();
            sinkAndSwim.setImage(sinkAndSwimRequest.getImage());
            sinkAndSwim.setAnswer(sinkAndSwimRequest.getAnswer());
            sinkAndSwim.setName(sinkAndSwimRequest.getName());
            sinkAndSwim.setActive(sinkAndSwimRequest.getActive());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                sinkAndSwim.setUser(user);
            }
            sinkAndSwimRepository.save(sinkAndSwim);

            return ResponseEntity.ok(new MessageResponse("Object Updated Successfully"));
        } catch (Exception e) {
            System.err.println("try " + e.getMessage());
            return ResponseEntity.ok(new MessageResponse("Failed to Update Object"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        return ResponseEntity.ok(sinkAndSwimRepository.findAllByUserAndActive(user, "1"));
    }

    @PostMapping("/get")
    public DataTablesOutput<?> getByUser(@RequestBody DataTablesInput input) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.isPresent() ? optionalUser.get() : null;

        GetByUserSpecification byUser = new GetByUserSpecification(new SearchCriteria("user", "=", user.getId()));
        return sinkAndSwimRepository.findAll(input, byUser);
    }

    @GetMapping("/get/by/{id}")
    public ResponseEntity<?> getByID(@PathVariable Long id) {
        return ResponseEntity.ok(sinkAndSwimRepository.findById(id));
    }

}
