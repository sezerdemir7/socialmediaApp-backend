package com.project.socialApp.controller;

import com.project.socialApp.dto.responses.PostResponse;
import com.project.socialApp.dto.responses.UserResponse;
import com.project.socialApp.entities.User;
import com.project.socialApp.exception.UserNotFoundException;
import com.project.socialApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.saveOneUser(newUser);
    }
    @GetMapping("{userId}")
    public UserResponse getOneUser(@PathVariable Long userId){
        //custom exception
        User user =userService.getOneUserByID(userId);
        if(user==null){
            throw new UserNotFoundException("User bulunamadı");
        }
        return new UserResponse(user) ;
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser){
        return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
      return userService.getUserActivity(userId);
    }



    @ExceptionHandler(UserNotFoundException.class)

    private ResponseEntity<String > handleUserNotFound(UserNotFoundException ex){

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
}
