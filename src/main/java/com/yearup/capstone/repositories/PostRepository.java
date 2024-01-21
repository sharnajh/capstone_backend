package com.yearup.capstone.repositories;

import com.yearup.capstone.models.Post;
import com.yearup.capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p.likedBy FROM Post p WHERE p.post_id = :postId")
//    Set<User> findUsersWhoLikedPost(@Param("postId") Long postId);

    // Method to find posts by user
    List<Post> findByUser(User user);
}
