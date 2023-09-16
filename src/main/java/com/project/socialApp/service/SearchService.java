package com.project.socialApp.service;


import com.project.socialApp.dto.responses.UserResponse;
import com.project.socialApp.entities.User;
import com.project.socialApp.repository.PostRepository;
import com.project.socialApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public List<UserResponse> searchUserByName(String name) {
        List <User> users=userRepository.findByUserNameContainingIgnoreCase(name);



        return users.stream().map(user->new UserResponse(
                user.getId(),
                user.getAvatar(),
                user.getUserName()
        )).collect(Collectors.toList());
    }
}
