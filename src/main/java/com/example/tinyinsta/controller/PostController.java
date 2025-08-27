package com.example.tinyinsta.controller;

import com.example.tinyinsta.model.Post;
import com.example.tinyinsta.model.User;
import com.example.tinyinsta.repository.PostRepository;
import com.example.tinyinsta.repository.FollowRepository;
import com.example.tinyinsta.repository.CustomPostRepository;
import com.example.tinyinsta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CustomPostRepository customPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    // Fil personnalisé : posts des followees et soi-même
    @GetMapping("")
    public List<Post> getFeed(@RequestParam Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return List.of();
        var followees = followRepository.findByFollower(userOpt.get())
            .stream().map(f -> f.getFollowee().getId()).toList();
        // Inclure soi-même
        var userIds = new java.util.ArrayList<Long>(followees);
        userIds.add(userId);
        return customPostRepository.findFeedPosts(userIds);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(
            @RequestParam("userId") Long userId,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        // Sauvegarde du fichier image
    File dir = new File(uploadDir);
    if (!dir.exists()) dir.mkdirs();
    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
    File dest = new File(dir, fileName);
    image.transferTo(dest);
    // Création du post
    Post post = new Post();
    post.setUser(userOpt.get());
    post.setDescription(description);
    post.setImagePath("/files/" + fileName); // URL d'accès
    postRepository.save(post);
    return ResponseEntity.ok(post);
    }
}
