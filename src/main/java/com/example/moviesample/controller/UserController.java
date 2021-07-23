package com.example.moviesample.controller;

import com.example.moviesample.configuration.security.JwtTokenUtil;
import com.example.moviesample.dto.api.UserDto;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@Validated
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;

    public UserController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Validated
    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto login(@Validated @RequestParam(value = "user") String username) {
        String token = jwtTokenUtil.generateJwtToken(username);
        UserDto user = new UserDto();
        user.setUser(username);
        user.setToken(token);
        return user;
    }


}
