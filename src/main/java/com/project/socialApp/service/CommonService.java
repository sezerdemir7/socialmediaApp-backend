package com.project.socialApp.service;

import com.project.socialApp.dto.responses.LikeResponse;
import com.project.socialApp.dto.responses.PostResponse;
import com.project.socialApp.entities.Like;
import com.project.socialApp.entities.Post;
import com.project.socialApp.repository.LikeRepository;
import com.project.socialApp.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

  //  private final LikeService likeService;


    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent()&& postId.isPresent()) {
            list= likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            list=likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list=likeRepository.findByPostId(postId.get());
        }
        else {
            list=likeRepository.findAll();
        }
        return list.stream().map(like -> new LikeResponse(
                like.getId(),
                like.getUser().getId(),
                like.getPost().getId()
        )).collect(Collectors.toList());
        // return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list= postRepository.findByUserId(userId.get());
        }else {
            list=postRepository.findAll();
        }


        List<PostResponse> responseList = list.stream()
                .map(post -> {
                    List<Like> likes=likeRepository.findByPostId(post.getId());
                    List<LikeResponse> likeResponses;

                    return new PostResponse(
                            post.getId(),
                            post.getUser().getId(),
                            post.getUser().getUserName(),
                            post.getTitle(),
                            post.getText(),
                            post.getCreateDate(),
                            null
                    );})
                .collect(Collectors.toList());

        return responseList;
    }
}

