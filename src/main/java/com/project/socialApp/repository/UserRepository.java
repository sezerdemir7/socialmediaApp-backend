package com.project.socialApp.repository;


import com.project.socialApp.dto.responses.UserResponse;
import com.project.socialApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String username);
    List<User> findByUserNameContainingIgnoreCase(String name);

}
