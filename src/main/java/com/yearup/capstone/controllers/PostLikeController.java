package com.yearup.capstone.controllers;

import com.yearup.capstone.models.PostLike;
import com.yearup.capstone.services.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postlikes")
public class PostLikeController {
    @Autowired
    private PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<?> addLike(@RequestParam Long postId, @RequestParam Long userId) {
        Optional<PostLike> existingLike = postLikeService.findLikeByUserIdAndPostId(userId, postId);
        if (existingLike.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Like already exists.");
        } else {
            try {
                PostLike newLike = postLikeService.addLike(postId, userId);
                return ResponseEntity.ok(newLike);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeLike(@RequestParam Long postId, @RequestParam Long userId) {
        boolean deleted = postLikeService.removeLike(postId, userId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by/post/{postId}")
    public ResponseEntity<List<PostLike>> getLikesByPostId(@PathVariable Long postId) {
        List<PostLike> likes = postLikeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/by/user/{userId}")
    public ResponseEntity<List<PostLike>> getLikesByUserId(@PathVariable Long userId) {
        List<PostLike> likes = postLikeService.getLikesByUserId(userId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/find")
    public ResponseEntity<PostLike> getLikeByUserIdAndPostId(@RequestParam Long userId, @RequestParam Long postId) {
        Optional<PostLike> postLike = postLikeService.findLikeByUserIdAndPostId(userId, postId);
        return postLike.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
