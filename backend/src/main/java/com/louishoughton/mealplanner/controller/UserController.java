package com.louishoughton.mealplanner.controller;

import com.louishoughton.mealplanner.model.User;
import com.louishoughton.mealplanner.model.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(JwtAuthenticationToken principal) {
        return ResponseEntity.of(userRepository.get(principal.getName()));
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(JwtAuthenticationToken principal, @RequestBody User user) {
        if (userRepository.get(principal.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        user.setGuid(principal.getName());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}
