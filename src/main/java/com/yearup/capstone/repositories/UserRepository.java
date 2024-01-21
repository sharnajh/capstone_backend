package com.yearup.capstone.repositories;

import com.yearup.capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM User u JOIN u.likedPosts p WHERE p.post_id = :postId")
//    List<User> findUsersWhoLikedPost(@Param("postId") Long postId);

    Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);
}
