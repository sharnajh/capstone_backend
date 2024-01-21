CREATE TABLE User (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50), -- google display name [0]
    lastName VARCHAR(50), -- google display name [1]
    email VARCHAR(50) UNIQUE, -- google email will go here
    password VARCHAR(255),
    photoURL VARCHAR(255), -- google profile picture
    bio VARCHAR(200),
    google_id VARCHAR(100), -- NULLABLE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP                                                     
);

CREATE TABLE Post (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    content TEXT,
    imageUrl VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

CREATE TABLE FollowRelationship (
    follower_id BIGINT,
    followed_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, followed_id),
    FOREIGN KEY (follower_id) REFERENCES User(user_id),
    FOREIGN KEY (followed_id) REFERENCES User(user_id)
);

CREATE TABLE PostLikes (
    post_id BIGINT,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES Post(post_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE PostComments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT,
    user_id BIGINT,
    content VARCHAR(280),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES Post(post_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);




