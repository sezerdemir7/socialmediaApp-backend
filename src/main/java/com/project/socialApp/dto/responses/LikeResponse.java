package com.project.socialApp.dto.responses;

import com.project.socialApp.entities.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long postId;
    /*
    public LikeResponse(Like entity){
        this.id=entity.getId();
        this.userId= entity.getUser().getId();
        this.postId=entity.getPost().getId();
    }

     */
}
