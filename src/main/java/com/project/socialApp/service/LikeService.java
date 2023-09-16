package com.project.socialApp.service;

import com.project.socialApp.dto.requests.LikeCreateRequest;
import com.project.socialApp.dto.responses.LikeResponse;
import com.project.socialApp.entities.Like;
import com.project.socialApp.entities.Post;
import com.project.socialApp.entities.User;
import com.project.socialApp.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final   PostService postService;

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

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user=userService.getOneUserByID(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());

        if(user!=null && post!=null){
            Like likeTosave=new Like();
            likeTosave.setId(request.getId());
            likeTosave.setUser(user);
            likeTosave.setPost(post);

           return likeRepository.save(likeTosave);
        }
        else {
            return null;
        }
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
