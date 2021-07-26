package com.example.moviesample.controller;

import com.example.moviesample.configuration.security.JwtTokenUtil;
import com.example.moviesample.configuration.security.MovieUserDetailService;
import com.example.moviesample.dto.api.AuthenticationRequestDto;
import com.example.moviesample.dto.api.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@Validated
public class UserController {

    private MovieUserDetailService movieUserDetailService;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public void setMovieUserDetailService(MovieUserDetailService movieUserDetailService) {
        this.movieUserDetailService = movieUserDetailService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Validated
    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto login(@Validated @RequestBody AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = movieUserDetailService.loadUserByUsername(request.getUsername());

        String token = jwtTokenUtil.generateJwtToken(userDetails.getUsername());
        UserDto user = new UserDto();
        user.setUser(request.getUsername());
        user.setToken(token);

        return user;
    }


}
