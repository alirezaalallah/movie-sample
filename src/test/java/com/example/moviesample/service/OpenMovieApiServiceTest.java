package com.example.moviesample.service;

import com.example.moviesample.dao.model.MovieType;
import com.example.moviesample.dto.OpenMovieResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OpenMovieApiServiceTest {

    @Autowired
    private OpenMovieApiService openMovieApiService;

    @Test
    public void givenNullTitle_thenOMDBApiSendsErrorResponseWithInvalidIdItIsMeansThatTitleOrIdIsNotPresent() {
        ResponseEntity<OpenMovieResponseDto> omdbResponseEntity = openMovieApiService.getMovieInfo(null);

        assertEquals(HttpStatus.OK, omdbResponseEntity.getStatusCode());
        assertNotNull(omdbResponseEntity.getBody());
        assertEquals(OpenMovieResponseDto.class, omdbResponseEntity.getBody().getClass());
        OpenMovieResponseDto response = omdbResponseEntity.getBody();

        assertErrorOMDBResponse("Incorrect IMDb ID.", response);
    }

    @Test
    public void givenInvalidName_thenOMDBApiSendsErrorResponseWithMovieNotFoundMessage() {
        String INVALID_MOVIE_NAME = "bbbbbbb";
        ResponseEntity<OpenMovieResponseDto> omdbResponseEntity = openMovieApiService.getMovieInfo(INVALID_MOVIE_NAME);

        assertEquals(HttpStatus.OK, omdbResponseEntity.getStatusCode());
        assertNotNull(omdbResponseEntity.getBody());
        assertEquals(OpenMovieResponseDto.class, omdbResponseEntity.getBody().getClass());
        OpenMovieResponseDto response = omdbResponseEntity.getBody();

        assertErrorOMDBResponse("Movie not found!", response);
    }

    @Test
    public void givenValidTitle_thenOMDBApiReturnsMovieInfo() {
        ResponseEntity<OpenMovieResponseDto> omdbResponseEntity = openMovieApiService.getMovieInfo("Batman");

        assertEquals(HttpStatus.OK, omdbResponseEntity.getStatusCode());
        assertNotNull(omdbResponseEntity.getBody());
        assertEquals(OpenMovieResponseDto.class, omdbResponseEntity.getBody().getClass());

        OpenMovieResponseDto response = omdbResponseEntity.getBody();
        assertEquals("True",response.getResponse());

        assertEquals("Batman", response.getTitle());
        assertEquals("1989", response.getYear());
        assertEquals("PG-13", response.getRated());
        assertEquals(LocalDate.parse("1989-06-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")), response.getReleased());
        assertEquals("Action, Adventure", response.getGenre());
        assertEquals("Tim Burton", response.getDirector());
        assertEquals("Bob Kane, Sam Hamm, Warren Skaaren", response.getWriter());
        assertEquals("Michael Keaton, Jack Nicholson, Kim Basinger", response.getActors());
        assertEquals("The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker."
                , response.getPlot());
        assertEquals("English, French, Spanish", response.getLanguage());
        assertEquals("Won 1 Oscar. 9 wins & 26 nominations total", response.getAwards());
        assertEquals(3, response.getRatings().size());
        assertEquals("United States, United Kingdom", response.getCountry());
        assertEquals("69", response.getMetaScore());
        assertEquals("7.5", response.getImdbRating());
        assertEquals("345,938", response.getImdbVotes());
        assertEquals("tt0096895", response.getImdbID());
        assertEquals(MovieType.MOVIE, response.getType());
        assertEquals(new BigDecimal(251348343), response.getBoxOffice());
    }

    private void assertErrorOMDBResponse(String expectedError, OpenMovieResponseDto response) {
        assertEquals("False", response.getResponse());
        assertEquals(expectedError, response.getError());

        assertNull(response.getTitle());
        assertNull(response.getYear());
        assertNull(response.getRated());
        assertNull(response.getReleased());
        assertNull(response.getGenre());
        assertNull(response.getDirector());
        assertNull(response.getWriter());
        assertNull(response.getActors());
        assertNull(response.getPlot());
        assertNull(response.getLanguage());
        assertNull(response.getAwards());
        assertNull(response.getRatings());
        assertNull(response.getCountry());
        assertNull(response.getMetaScore());
        assertNull(response.getImdbRating());
        assertNull(response.getImdbVotes());
        assertNull(response.getImdbID());
        assertNull(response.getType());
        assertNull(response.getBoxOffice());
    }
}