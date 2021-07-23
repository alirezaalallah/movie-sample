package com.example.moviesample.controller;

import com.example.moviesample.dto.api.ApiResponseDto;
import com.example.moviesample.dto.api.RateRequestDto;
import com.example.moviesample.service.MovieService;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/movie")
@Validated
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> get(@Valid @RequestParam("title") @NotNull(message = "title is required") String title) {
        ApiResponseDto apiResponseDto = movieService.checkMovieWonState(title);

        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PostMapping(value = "/rate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> rate(@Valid @RequestBody RateRequestDto rateRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ApiResponseDto apiResponseDto = movieService.rate(rateRequestDto, auth.getPrincipal().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDto);
    }

    @GetMapping(value = "/top-ten")
    public ResponseEntity<ApiResponseDto> topTen(@Valid
                                                 @RequestParam(value = "page", defaultValue = "0")
                                                 @Range(min = 0, max = 10, message = "Invalid range for page param")
                                                         Integer page) {
        ApiResponseDto apiResponseDto = movieService.getTopTen(page);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseDto);
    }

}
