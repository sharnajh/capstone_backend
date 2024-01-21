package com.yearup.capstone.services;

import com.yearup.capstone.models.Post;
import com.yearup.capstone.models.PostLike;
import com.yearup.capstone.models.PostLikeId;
import com.yearup.capstone.models.User;
import com.yearup.capstone.repositories.PostLikeRepository;
import com.yearup.capstone.repositories.PostRepository;
import com.yearup.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostLikeService {
    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostLike addLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        PostLike postLike = new PostLike(new PostLikeId(postId, userId));
        postLike.setPost(post);
        postLike.setUser(user);
        return postLikeRepository.save(postLike);
    }

    public boolean removeLike(Long postId, Long userId) {
        Optional<PostLike> like = postLikeRepository.findByIdUserIdAndIdPostId(userId, postId);
        if (like.isPresent()) {
            postLikeRepository.delete(like.get());
            return true;
        }
        return false;
    }

    public List<PostLike> getLikesByPostId(Long postId) {
        return postLikeRepository.findByIdPostId(postId);
    }

    public List<PostLike> getLikesByUserId(Long userId) {
        return postLikeRepository.findByIdUserId(userId);
    }

    public Optional<PostLike> findLikeByUserIdAndPostId(Long userId, Long postId) {
        return postLikeRepository.findByIdUserIdAndIdPostId(userId, postId);
    }
}
