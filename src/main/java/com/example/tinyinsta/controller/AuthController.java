
package com.example.tinyinsta.controller;

import com.example.tinyinsta.model.User;
import com.example.tinyinsta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/users")
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        // ATTENTION : mot de passe en clair pour la démo !
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            // Auth simplifiée : retourne juste l'id utilisateur
            return ResponseEntity.ok(found.get().getId());
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
