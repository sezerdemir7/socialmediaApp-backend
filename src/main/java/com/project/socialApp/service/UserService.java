package com.project.socialApp.service;

import com.project.socialApp.entities.Comment;
import com.project.socialApp.entities.Like;
import com.project.socialApp.entities.Post;
import com.project.socialApp.entities.User;
import com.project.socialApp.repository.CommentRepository;
import com.project.socialApp.repository.LikeRepository;
import com.project.socialApp.repository.PostRepository;
import com.project.socialApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        newUser.setAvatar(0);
        return userRepository.save(newUser);
    }

    public User getOneUserByID(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser=user.get();
            foundUser.setUserName(newUser.getUserName());
           // foundUser.setPassword(user.getPassword());
            foundUser.setAvatar(newUser.getAvatar());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds=postRepository.findTopByUserId(userId);
        if (postIds.isEmpty()){

            return null;
        }

      List<Object> comments= commentRepository.findUserCommentByPostId(postIds);
        List<Object> likes=likeRepository.findUserLikesByPostId(postIds);

        List<Object> result=new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);

        return result;
    }



}
