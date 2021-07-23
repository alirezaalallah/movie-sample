package com.example.moviesample.controller;

import com.example.moviesample.configuration.security.JwtTokenUtil;
import com.example.moviesample.dao.model.Movie;
import com.example.moviesample.dao.model.MovieType;
import com.example.moviesample.dao.model.Rate;
import com.example.moviesample.dto.TopRateMovieDto;
import com.example.moviesample.dto.api.ApiErrorDto;
import com.example.moviesample.dto.api.ApiResponseDto;
import com.example.moviesample.dto.api.RateRequestDto;
import com.example.moviesample.dto.api.Reason;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String GET_MOVIE_INFO = "/api/v1/movie/info";
    private static final String GIVE_RATE_TO_MOVIE = "/api/v1/movie/rate";
    private static final String GET_TOP_TEN_RATED = "/api/v1/movie/top-ten";
    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";

    @Test
    public void givenNullTitle_thenReturnValidationErrors() throws Exception {
        ApiErrorDto error = ApiErrorDto.builder().reason(Reason.REQUIRED_FIELD).param("title").message("Param is missing").build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(error).badRequest().build();

        mvc.perform(get(GET_MOVIE_INFO).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    public void givenWonMovieTitle_thenReturnMovieInfoWithWonStatus() throws Exception {
        Movie batmanMovie = Movie.builder()
                .title("Batman")
                .won(true)
                .year("1989")
                .rated("PG-13")
                .released(LocalDate.parse("1989-06-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .genre("Action, Adventure")
                .director("Tim Burton")
                .writer("Bob Kane, Sam Hamm, Warren Skaaren")
                .actors("Michael Keaton, Jack Nicholson, Kim Basinger")
                .plot("The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker.")
                .language("English, French, Spanish")
                .awards("Won 1 Oscar. 9 wins & 26 nominations total")
                .country("United States, United Kingdom")
                .metaScore("69")
                .imdbRating("7.5")
                .imdbVotes("345,938")
                .imdbID("tt0096895")
                .ratings(new ArrayList<>() {{
                    add(Rate.builder().source("Internet Movie Database").value("7.5/10").build());
                    add(Rate.builder().source("Rotten Tomatoes").value("71%").build());
                    add(Rate.builder().source("Metacritic").value("69/100").build());
                }})
                .type(MovieType.MOVIE)
                .boxOffice(new BigDecimal(251348343))
                .build();

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().result(batmanMovie).ok().build();

        mvc.perform(get(GET_MOVIE_INFO).param("title", "Batman").header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    public void givenNotWonMovieTitle_thenReturnMovieInfoWithWonStatus() throws Exception {
        Movie batmanMovie = Movie.builder()
                .title("Dude, Where's My Car?")
                .won(false)
                .year("2000")
                .rated("PG-13")
                .released(LocalDate.parse("2000-12-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .genre("Comedy, Mystery, Sci-Fi")
                .director("Danny Leiner")
                .writer("Philip Stark")
                .actors("Ashton Kutcher, Seann William Scott, Jennifer Garner, Marla Sokoloff")
                .plot("Two potheads wake up after a night of partying and cannot remember where they parked their car.")
                .language("English, Japanese, French")
                .awards("6 nominations.")
                .country("USA")
                .metaScore("30")
                .imdbRating("5.5")
                .imdbVotes("135,872")
                .imdbID("tt0242423")
                .ratings(new ArrayList<>() {{
                    add(Rate.builder().source("Internet Movie Database").value("5.5/10").build());
                    add(Rate.builder().source("Rotten Tomatoes").value("17%").build());
                    add(Rate.builder().source("Metacritic").value("30/100").build());
                }})
                .type(MovieType.MOVIE)
                .boxOffice(new BigDecimal(46729800))
                .build();

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().result(batmanMovie).ok().build();

        mvc.perform(get(GET_MOVIE_INFO).param("title", "car").header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    public void givenNotExistsMovieTitle_thenReturnMovieNotExistsErrorFromOMDB() throws Exception {
        ApiErrorDto error = ApiErrorDto.builder().reason(Reason.OMDB_API_BUSINESS_ERROR).message("Movie not found!").build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(error).badRequest().build();

        mvc.perform(get(GET_MOVIE_INFO).param("title", "bbbbbbbb").header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));

    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenRateToMovieTitle_thenReturnSuccessResponse() throws Exception {
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().created().build();
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("9").build();

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenInvalidDataToRateService_thenReturnValidationErrorResponse() throws Exception {
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("trtgretged").build();

        ApiErrorDto error = ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("value").message("value should be between 0 and 10").build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(error).badRequest().build();

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenNullValueToRateService_thenReturnValidationErrorResponse() throws Exception {
        RateRequestDto rateRequestDto = RateRequestDto.builder().build();

        List<ApiErrorDto> errors = new ArrayList<>() {{
            add(ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("title").message("title is required").build());
            add(ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("value").message("value is required").build());
        }};

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(errors).badRequest().build();

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenInvalidRangeValueToRateService_thenReturnValidationErrorResponse() throws Exception {
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("11").build();

        List<ApiErrorDto> errors = new ArrayList<>() {{
            add(ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("value").message("value should be between 0 and 10").build());
        }};

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(errors).badRequest().build();

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenRateToNotExistsMovie_thenReturnOMDBBusinessErrorResponse() throws Exception {
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("bbbbbbb").value("10").build();

        List<ApiErrorDto> errors = new ArrayList<>() {{
            add(ApiErrorDto.builder().reason(Reason.OMDB_API_BUSINESS_ERROR).message("Movie not found!").build());
        }};

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(errors).badRequest().build();

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql"})
    public void givenDuplicateRateToMovie_thenReturnDuplicateResponseErrorButAnotherUserCanGiveRate() throws Exception {
        RateRequestDto rateRequestDto = RateRequestDto.builder().title("Batman").value("9").build();

        ApiResponseDto successRateResponse = ApiResponseDto.builder().created().build();
        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(successRateResponse)));


        List<ApiErrorDto> errors = new ArrayList<>() {{
            add(ApiErrorDto.builder().reason(Reason.USER_DUPLICATE_RATE).message("User has rated before").build());
        }};
        ApiResponseDto duplicateRateResponse = ApiResponseDto.builder().errors(errors).badRequest().build();
        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(duplicateRateResponse)));

        mvc.perform(post(GIVE_RATE_TO_MOVIE).content(asJsonString(rateRequestDto)).header(AUTHORIZATION_HEADER, createJwtHeader(USER_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(successRateResponse)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/topRate.sql"})
    public void givenInvalidPageParamValueToGetTopTenRateMovie_thenReturnGetValidationError() throws Exception {
        List<ApiErrorDto> errors = new ArrayList<>() {{
            add(ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("page").message("Invalid range for page param").build());
        }};

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().errors(errors).badRequest().build();
        mvc.perform(get(GET_TOP_TEN_RATED).param("page", "12").header(AUTHORIZATION_HEADER, createJwtHeader(USER_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/topRate.sql"})
    public void givenRequestToGetTopTenRateMovies_thenReturnListOfTopTenMoviesOrderedByBoxOffice() throws Exception {
        List<TopRateMovieDto> topRateMovies = new ArrayList<>(){{
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

        ApiResponseDto apiResponseDto = ApiResponseDto.builder().result(topRateMovies).ok().build();

        mvc.perform(get(GET_TOP_TEN_RATED).param("page", "0").header(AUTHORIZATION_HEADER, createJwtHeader(USER_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(apiResponseDto)));
    }

    private String createJwtHeader(String username) {
        return jwtTokenUtil.generateJwtToken(username);
    }

    public String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}