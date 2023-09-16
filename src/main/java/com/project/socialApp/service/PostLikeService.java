package com.project.socialApp.service;

import com.project.socialApp.dto.responses.LikeResponse;
import com.project.socialApp.entities.Like;
import com.project.socialApp.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostLikeService {

    private final LikeRepository likeRepository;

    public List<LikeResponse> getAllLikesWithParamOf(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like -> new LikeResponse(
                like.getId(),
                like.getUser().getId(),
                like.getPost().getId()
                )).collect(Collectors.toList());
    }
}
