package com.yearup.capstone.services;

import com.yearup.capstone.models.User;
import com.yearup.capstone.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User editUser(Long id, User userDetails) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update fields of existingUser with userDetails
            // Avoid overwriting the password directly, especially if it's encoded
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setBio(userDetails.getBio());
            existingUser.setPhotoURL(userDetails.getPhotoURL());
            // Include other fields that you allow to be updated

            // Save the updated user
            return userRepository.save(existingUser);
        }
        return null; // Or handle this case as per your requirement
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Remove the association between posts and user
            userRepository.delete(user);
        }
    }

//    public void followUser(Long followerId, Long followedId) {
//        Optional<User> follower = userRepository.findById(followerId);
//        Optional<User> followed = userRepository.findById(followedId);
//
//        if (follower.isPresent() && followed.isPresent()) {
//            follower.get().getFollowing().add(followed.get());
//            followed.get().getFollowers().add(follower.get());
//            userRepository.save(follower.get());
//            userRepository.save(followed.get());
//        }
//    }
//
//    public void unfollowUser(Long followerId, Long followedId) {
//        Optional<User> follower = userRepository.findById(followerId);
//        Optional<User> followed = userRepository.findById(followedId);
//
//        if (follower.isPresent() && followed.isPresent()) {
//            follower.get().getFollowing().remove(followed.get());
//            followed.get().getFollowers().remove(follower.get());
//            userRepository.save(follower.get());
//            userRepository.save(followed.get());
//        }
//    }

//    public List<User> getUsersWhoLikedPost(Long postId) {
//        return userRepository.findUsersWhoLikedPost(postId);
//    }

}
