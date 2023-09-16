package com.project.socialApp.service;

import com.project.socialApp.dto.requests.PostCreateRequest;
import com.project.socialApp.dto.requests.PostUpdateRequest;
import com.project.socialApp.dto.responses.LikeResponse;
import com.project.socialApp.dto.responses.PostResponse;
import com.project.socialApp.entities.Post;
import com.project.socialApp.entities.User;
import com.project.socialApp.repository.PostRepository;
import com.project.socialApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostLikeService postLikeService;



    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;

        if(userId.isPresent()){
            list= postRepository.findByUserId(userId.get());
        }else {
            list=postRepository.findAll();
        }


        return getPostResponse(list);
    }


    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostResponse getOnePostByIdWithLikes(Long postId) {
        Post post= postRepository.findById(postId).orElse(null);;
        List<LikeResponse> likes=postLikeService.getAllLikesWithParamOf(Optional.ofNullable(null),Optional.ofNullable(postId));

        return new PostResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getText(),
                post.getCreateDate(),
                likes
        );
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
       User user= userService.getOneUserByID(newPostRequest.getUserId());
       if(user==null){
           return null;
       }
       Post toSave=new Post();
       toSave.setId(newPostRequest.getId());
       toSave.setText(newPostRequest.getText());
       toSave.setTitle(newPostRequest.getTitle());
       toSave.setUser(user);
       toSave.setCreateDate(new Date());
        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePostRequest) {
        Optional<Post> post=postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate=post.get();
            toUpdate.setText(updatePostRequest.getText());
            toUpdate.setTitle(updatePostRequest.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }


    public List<PostResponse> getPostByUserId(Optional<Long> userId) {
        if (userId.isPresent()) {
            List<Post> list = postRepository.findByUserId(userId.get());
            return getPostResponse(list);
        } else {

            return Collections.emptyList();
        }
    }


    public List<PostResponse> getPostResponse(List<Post> list){
        List<PostResponse> responseList = list.stream()
                .map(post -> {

                    List<LikeResponse> likes=postLikeService.getAllLikesWithParamOf(Optional.ofNullable(null),Optional.ofNullable(post.getId()));

                    return new PostResponse(
                            post.getId(),
                            post.getUser().getId(),
                            post.getUser().getUserName(),
                            post.getTitle(),
                            post.getText(),
                            post.getCreateDate(),
                            likes
                    );})
                .collect(Collectors.toList());

        return responseList;
    }
}
