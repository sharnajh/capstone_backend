package com.yearup.capstone.services;

import com.yearup.capstone.models.Post;
import com.yearup.capstone.models.User;
import com.yearup.capstone.repositories.PostLikeRepository;
import com.yearup.capstone.repositories.PostRepository;
import com.yearup.capstone.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    final PostLikeRepository postLikeRepository;

    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            PostLikeRepository postLikeRepository
            ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {

        return postRepository.saveAndFlush(post);
    }

    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post editPost(Long id, Post post) {
        Post existingPost = postRepository.getOne(id);
        BeanUtils.copyProperties(post, existingPost);
        return postRepository.saveAndFlush(existingPost);
    }

    @Transactional
    public void deletePost(Long id) {
        postLikeRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

    public Post updatePost(Post updatedPost) {
        // Check if the post with the given ID exists
        Long postId = updatedPost.getPost_id();
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        // Update the existing post with the fields from the updatedPost
        existingPost.setContent(updatedPost.getContent());
        existingPost.setImageUrl(updatedPost.getImageUrl());

        // Save and return the updated post
        return postRepository.save(existingPost);
    }

//    public Set<User> getUsersWhoLikedPost(Long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
//
//        return post.getLikedBy();
//    }
}
