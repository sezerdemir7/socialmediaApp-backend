package com.project.socialApp.repository;

import com.project.socialApp.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByUserId(Long userId);
}
