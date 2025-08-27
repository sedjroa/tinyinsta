package com.example.tinyinsta.controller;

import com.example.tinyinsta.model.Follow;
import com.example.tinyinsta.model.User;
import com.example.tinyinsta.repository.FollowRepository;
import com.example.tinyinsta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestParam Long followerId, @RequestParam Long followeeId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> followee = userRepository.findById(followeeId);
        if (follower.isEmpty() || followee.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if (followRepository.existsByFollowerAndFollowee(follower.get(), followee.get())) {
            return ResponseEntity.badRequest().body("Already following");
        }
        Follow f = new Follow();
        f.setFollower(follower.get());
        f.setFollowee(followee.get());
        followRepository.save(f);
        return ResponseEntity.ok("Followed");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam Long followerId, @RequestParam Long followeeId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> followee = userRepository.findById(followeeId);
        if (follower.isEmpty() || followee.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        followRepository.deleteByFollowerAndFollowee(follower.get(), followee.get());
        return ResponseEntity.ok("Unfollowed");
    }

    @GetMapping("/followees")
    public List<Follow> getFollowees(@RequestParam Long followerId) {
        Optional<User> follower = userRepository.findById(followerId);
        return follower.map(f -> followRepository.findByFollower(f)).orElse(List.of());
    }

    @GetMapping("/followers")
    public List<Follow> getFollowers(@RequestParam Long followeeId) {
        Optional<User> followee = userRepository.findById(followeeId);
        return followee.map(f -> followRepository.findByFollowee(f)).orElse(List.of());
    }
}
