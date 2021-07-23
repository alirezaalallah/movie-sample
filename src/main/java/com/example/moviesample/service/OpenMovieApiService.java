package com.example.moviesample.service;

import com.example.moviesample.dto.OpenMovieResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenMovieApiService {
    private RestTemplate restTemplate;

    @Value("${openMovieDatabase.url}")
    private String restUrl;

    @Value("${openMovieDatabase.apiKey}")
    private String apiKey;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<OpenMovieResponseDto> getMovieInfo(String title) {
        return restTemplate.getForEntity(restUrl + "/?t={title}&apiKey={apiKey}", OpenMovieResponseDto.class, title, apiKey);
    }

}
