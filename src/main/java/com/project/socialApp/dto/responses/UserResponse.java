package com.project.socialApp.dto.responses;

import com.project.socialApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private  Long id;
    private Integer avatarId;
    private String userName;

    /*public UserResponse(User entity) {
        this.id=entity.getId();
        this.avatarId= entity.getAvatar();
        this.userName= entity.getUserName();
    }
    
     */

    public UserResponse(User user) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.userName = user.getUserName();
    }



}
