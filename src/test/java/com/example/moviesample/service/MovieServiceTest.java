package com.example.moviesample.service;

import com.example.moviesample.dao.model.Movie;
import com.example.moviesample.dao.model.MovieType;
import com.example.moviesample.dto.TopRateMovieDto;
import com.example.moviesample.dto.api.ApiResponseDto;
import com.example.moviesample.dto.api.RateRequestDto;
import com.example.moviesample.exception.OMDBBusinessException;
import com.example.moviesample.exception.UserDuplicateRateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void givenValidTitle_thenReturnSuccessApiResponseAndMovieIsWon() {
        ApiResponseDto apiResponseDto = movieService.checkMovieWonState("Batman");

        assertEquals(HttpStatus.OK, apiResponseDto.getStatus());
        assertNull(apiResponseDto.getErrors());
        assertNotNull(apiResponseDto.getResult());
        assertEquals(Movie.class, apiResponseDto.getResult().getClass());
        Movie movie = (Movie) apiResponseDto.getResult();
        assertEquals("Batman", movie.getTitle());
        assertTrue(movie.getWon());
        assertEquals("1989", movie.getYear());
        assertEquals("PG-13", movie.getRated());
        assertEquals(LocalDate.parse("1989-06-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")), movie.getReleased());
        assertEquals("Action, Adventure", movie.getGenre());
        assertEquals("Tim Burton", movie.getDirector());
        assertEquals("Bob Kane, Sam Hamm, Warren Skaaren", movie.getWriter());
        assertEquals("Michael Keaton, Jack Nicholson, Kim Basinger", movie.getActors());
        assertEquals("The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker."
                , movie.getPlot());
        assertEquals("English, French, Spanish", movie.getLanguage());
        assertEquals("Won 1 Oscar. 9 wins & 26 nominations total", movie.getAwards());
        assertEquals(3, movie.getRatings().size());
        assertEquals("United States, United Kingdom", movie.getCountry());
        assertEquals("69", movie.getMetaScore());
        assertEquals("7.5", movie.getImdbRating());
        assertEquals("345,938", movie.getImdbVotes());
        assertEquals("tt0096895", movie.getImdbID());
        assertEquals(MovieType.MOVIE, movie.getType());
        assertEquals(new BigDecimal(251348343), movie.getBoxOffice());
    }

    @Test
    public void givenValidTitle_thenReturnSuccessApiResponseAndMovieIsNotWon() {
        ApiResponseDto apiResponseDto = movieService.checkMovieWonState("Car");

        assertEquals(HttpStatus.OK, apiResponseDto.getStatus());
        assertNull(apiResponseDto.getErrors());
        assertNotNull(apiResponseDto.getResult());
        assertEquals(Movie.class, apiResponseDto.getResult().getClass());
        Movie movie = (Movie) apiResponseDto.getResult();
        assertEquals("Dude, Where's My Car?", movie.getTitle());
        assertFalse(movie.getWon());

        assertEquals("2000", movie.getYear());
        assertEquals("PG-13", movie.getRated());
        assertEquals(LocalDate.parse("2000-12-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")), movie.getReleased());
        assertEquals("Comedy, Mystery, Sci-Fi", movie.getGenre());
        assertEquals("Danny Leiner", movie.getDirector());
        assertEquals("Philip Stark", movie.getWriter());
        assertEquals("Ashton Kutcher, Seann William Scott, Jennifer Garner", movie.getActors());
        assertEquals("Two potheads wake up after a night of partying and cannot remember where they parked their car.", movie.getPlot());
        assertEquals("English, Japanese, French", movie.getLanguage());
        assertEquals("6 nominations", movie.getAwards());
        assertEquals("United States", movie.getCountry());
        assertEquals(3, movie.getRatings().size());
        assertEquals("30", movie.getMetaScore());
        assertEquals("5.5", movie.getImdbRating());
        assertEquals("136,194", movie.getImdbVotes());
        assertEquals("tt0242423", movie.getImdbID());
        assertEquals(MovieType.MOVIE, movie.getType());
        assertEquals(new BigDecimal(46729800), movie.getBoxOffice());
    }

    @Test
    public void givenNullTitle_thenThrowsOMDBBusinessException() {
        OMDBBusinessException exception = assertThrows(OMDBBusinessException.class, () -> {
            movieService.checkMovieWonState(null);
        });
        assertEquals("Incorrect IMDb ID.", exception.getMessage());
    }

    @Test
    public void givenInvalidName_thenOMDBReturnOMDBBusinessException() {
        String INVALID_MOVIE_NAME = "bbbbbbb";
        OMDBBusinessException exception =
                assertThrows(OMDBBusinessException.class, () -> movieService.checkMovieWonState(INVALID_MOVIE_NAME));
        assertEquals("Movie not found!", exception.getMessage());
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenRateToValidMovieTitle_thenReturnOk() {
        String user = "user1";
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("9").build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().created().build();
        ApiResponseDto actualResponse = movieService.rate(rateRequestDto, user);
        assertEquals(expectedResponse.getErrors(), actualResponse.getErrors());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getResult(), actualResponse.getResult());
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenRateToInvalidMovieTitle_thenOMDBReturnOMDBBusinessException() {
        String user = "user1";
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("bbbbbbbb").value("9").build();

        OMDBBusinessException exception = assertThrows(OMDBBusinessException.class, () -> {
            movieService.rate(rateRequestDto, user);
        });
        assertEquals(exception.getMessage(), "Movie not found!");
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenTwistRateToMovieTitle_thenUserDuplicateRateExceptionWillOccur() {
        String user = "user1";
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("9").build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().created().build();
        ApiResponseDto actualResponse = movieService.rate(rateRequestDto, user);
        assertEquals(expectedResponse.getErrors(), actualResponse.getErrors());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getResult(), actualResponse.getResult());

        UserDuplicateRateException exception = assertThrows(UserDuplicateRateException.class, () -> {
            movieService.rate(rateRequestDto, user);
        });
        assertEquals(exception.getMessage(), "User has rated before");
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/topRate.sql"})
    public void givenRequestToGetTopTenRateMovies_thenReturnListOfTopTenMoviesOrderedByBoxOffice() {
        List<TopRateMovieDto> expectedTopRateMovies = new ArrayList<>() {{
            add(TopRateMovieDto.builder().name("Test-Moive9").rate(10d).boxOffice(new BigDecimal("29000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive8").rate(10d).boxOffice(new BigDecimal("28000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive7").rate(10d).boxOffice(new BigDecimal("27000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive6").rate(10d).boxOffice(new BigDecimal("26000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive5").rate(10d).boxOffice(new BigDecimal("25000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive4").rate(10d).boxOffice(new BigDecimal("24000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive3").rate(10d).boxOffice(new BigDecimal("23000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive2").rate(10d).boxOffice(new BigDecimal("22000.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive10").rate(10d).boxOffice(new BigDecimal("21100.00")).build());
            add(TopRateMovieDto.builder().name("Test-Moive2").rate(10d).boxOffice(new BigDecimal("21000.00")).build());
        }};

        ApiResponseDto actualResponse = movieService.getTopTen(0);

        assertNotNull(actualResponse.getResult());
        assertNull(actualResponse.getErrors());
        assertEquals(HttpStatus.OK, actualResponse.getStatus());

        List<TopRateMovieDto> actualTopTenMovies = (List<TopRateMovieDto>) actualResponse.getResult();
        assertEquals(10, actualTopTenMovies.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(expectedTopRateMovies.get(i).getName(), actualTopTenMovies.get(i).getName());
            assertEquals(expectedTopRateMovies.get(i).getBoxOffice(), actualTopTenMovies.get(i).getBoxOffice());
            assertEquals(expectedTopRateMovies.get(i).getRate(), actualTopTenMovies.get(i).getRate());
        }
    }
}