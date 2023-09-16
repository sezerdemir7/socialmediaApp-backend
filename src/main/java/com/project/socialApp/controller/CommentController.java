package com.project.socialApp.controller;

import com.project.socialApp.dto.requests.CommentCreateRequest;
import com.project.socialApp.dto.requests.CommentUpdateRequest;
import com.project.socialApp.dto.responses.CommentResponse;
import com.project.socialApp.entities.Comment;
import com.project.socialApp.entities.Post;
import com.project.socialApp.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentResponse> getAllComment(@RequestParam Optional<Long> userId,
                                               @RequestParam Optional<Long> postId){
        return commentService.getAllCommentWithParam(userId,postId);

    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateOneCommentById(commentId,request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }



}
