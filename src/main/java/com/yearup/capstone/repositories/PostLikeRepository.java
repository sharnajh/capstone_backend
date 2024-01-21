package com.yearup.capstone.repositories;

import com.yearup.capstone.models.PostLike;
import com.yearup.capstone.models.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    List<PostLike> findByIdPostId(Long postId);
    List<PostLike> findByIdUserId(Long userId);
    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.id.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);
    Optional<PostLike> findByIdUserIdAndIdPostId(Long userId, Long postId);
}
