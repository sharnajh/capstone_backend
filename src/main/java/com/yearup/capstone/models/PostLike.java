package com.yearup.capstone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "PostLike")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostLike {
    @EmbeddedId
    private PostLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public PostLike(PostLikeId id) {
        this.id = id;
    }

    public PostLike() {
    }

    public PostLikeId getId() {
        return id;
    }

    public void setId(PostLikeId id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
