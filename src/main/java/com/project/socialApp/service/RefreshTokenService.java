package com.project.socialApp.service;

import com.project.socialApp.entities.RefreshToken;
import com.project.socialApp.entities.User;
import com.project.socialApp.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

  /*  @Value("${refresh.token.expires.in}")
    private Long expireSeconds;

   */

    private final RefreshTokenRepository refreshTokenRepository;


    public String createRefreshToken(User user){
        RefreshToken token=new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(604800)));
        refreshTokenRepository.save(token);
        return token.getToken();

    }

    public boolean isRefreshExpired(RefreshToken token){
        return token.getExpiryDate().before(new Date());
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
