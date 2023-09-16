package com.project.socialApp.controller;

import com.project.socialApp.dto.requests.RefreshRequest;
import com.project.socialApp.dto.requests.UserRequest;
import com.project.socialApp.dto.responses.AuthResponse;
import com.project.socialApp.entities.RefreshToken;
import com.project.socialApp.entities.User;
import com.project.socialApp.security.JwtTokenProvider;
import com.project.socialApp.service.RefreshTokenService;
import com.project.socialApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());

        Authentication auth=authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken=jwtTokenProvider.generateJwtToken(auth);
        User user =userService.getOneUserByUserName(loginRequest.getUserName());
        AuthResponse authResponse=new AuthResponse();
        authResponse.setAccessToken( "Bearer "+jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return authResponse;

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest){
        AuthResponse authResponse = new AuthResponse();
        if (userService.getOneUserByUserName(registerRequest.getUserName()) !=null){
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken=jwtTokenProvider.generateJwtToken(auth);


        authResponse.setMessage("User successfully regitered");
        authResponse.setAccessToken("Bearer "+jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public  ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        AuthResponse response=new AuthResponse();
      RefreshToken token= refreshTokenService.getByUser(refreshRequest.getUserId());
      if(token.getToken().equals(refreshRequest.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token)){

          User user=token.getUser();


          String jwtToken=jwtTokenProvider.generateJwtTokenByUserName(user.getId());

          response.setMessage("token successfully refreshed");
          response.setAccessToken("Bearer "+jwtToken);
          response.setRefreshToken(refreshTokenService.createRefreshToken(user));
          response.setUserId(user.getId());
          return new ResponseEntity<>(response,HttpStatus.OK);
      }else{
          response.setMessage("refresh token is not valid");
          return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
      }
    }

}
