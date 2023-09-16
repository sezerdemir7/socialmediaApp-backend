package com.project.socialApp.controller;


import com.project.socialApp.dto.responses.UserResponse;
import com.project.socialApp.entities.User;
import com.project.socialApp.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/user/{name}")
    public ResponseEntity<List<UserResponse>> searchUserByName(@PathVariable String name) {
        List<UserResponse> users = searchService.searchUserByName(name);
        return ResponseEntity.ok(users);
    }



}
