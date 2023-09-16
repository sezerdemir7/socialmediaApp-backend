package com.project.socialApp.dto.requests;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class LikeCreateRequest {
    private Long id;
    private Long userId;
    private Long postId;
}
