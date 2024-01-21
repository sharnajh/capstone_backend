package com.yearup.capstone.controllers;

import com.yearup.capstone.models.Post;
import com.yearup.capstone.models.User;
import com.yearup.capstone.repositories.PostRepository;
import com.yearup.capstone.repositories.UserRepository;
import com.yearup.capstone.services.PostService;
import com.yearup.capstone.services.UserService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    public PostController(
            PostService postService,
            UserRepository userRepository,
            UserService userService,
            PostRepository postRepository) {
        this.postService = postService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("user_id")).longValue();
        String content = (String) request.get("content");
        String imageUrl = (String) request.get("imageUrl");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                Post post = new Post();
                post.setUser(user);
                post.setContent(content);
                post.setImageUrl(imageUrl);
                return postService.createPost(post);
            }
        }
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<Post> posts = postService.getPostsByUser(user);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Post existingPost = postService.getPostById(id);

            if (existingPost == null) {
                return ResponseEntity.notFound().build();
            }

            // Extract updated content and imageURL from the request
            String updatedContent = (String) request.get("content");
            String updatedImageUrl = (String) request.get("imageUrl");

            // Update the post if new content or imageURL is provided
            if (updatedContent != null) {
                existingPost.setContent(updatedContent);
            }

            if (updatedImageUrl != null) {
                existingPost.setImageUrl(updatedImageUrl);
            }

            // Save the updated post
            Post updatedPost = postService.updatePost(existingPost);

            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
