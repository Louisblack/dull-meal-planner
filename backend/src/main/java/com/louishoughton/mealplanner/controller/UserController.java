package com.louishoughton.mealplanner.controller;

import com.louishoughton.mealplanner.model.User;
import com.louishoughton.mealplanner.model.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestHeader("userGuid") String userGuid) {
        return ResponseEntity.of(userRepository.get(userGuid));
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}
