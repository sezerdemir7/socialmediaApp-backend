package com.project.socialApp.config;

import com.project.socialApp.security.JwtAuthenticationEntryPoint;
import com.project.socialApp.security.JwtAuthenticationFilter;
import com.project.socialApp.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig  {

    private final UserDetailServiceImpl userDetailService;
    private final JwtAuthenticationEntryPoint handler;


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return  new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable())
                .exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(handler))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/posts/user/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/search/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/comments/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/comments/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/avatars/**").permitAll()
                                .requestMatchers("/user/**").permitAll()//avatar
                                .requestMatchers(HttpMethod.POST,"/user/**").permitAll()//avatar
                                .requestMatchers(HttpMethod.PUT,"/user/**").permitAll()//avatar
                                .requestMatchers(HttpMethod.POST,"/likes/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/likes").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/likes/**").permitAll()

                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/users/activity/**").permitAll()

                                .anyRequest().authenticated());

               /* .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/posts")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/comments")
                .permitAll()
                .antMatchers("/auth/**")
                .permitAll()
                .anyRequest().authenticated();

                */

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }




}
