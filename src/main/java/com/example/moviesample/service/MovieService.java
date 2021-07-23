package com.example.moviesample.service;

import com.example.moviesample.dao.model.Movie;
import com.example.moviesample.dao.model.UserRate;
import com.example.moviesample.dao.model.UserRateRepository;
import com.example.moviesample.dto.OpenMovieResponseDto;
import com.example.moviesample.dto.TopRateMovieDto;
import com.example.moviesample.dto.api.RateRequestDto;
import com.example.moviesample.dto.api.ApiResponseDto;
import com.example.moviesample.exception.ApiCommunicationException;
import com.example.moviesample.exception.ApiEmptyResultException;
import com.example.moviesample.exception.OMDBBusinessException;
import com.example.moviesample.exception.UserDuplicateRateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private OpenMovieApiService openMovieApiService;

    private OscarCandidateService oscarCandidateService;

    private UserRateRepository userRateRepository;

    @Autowired
    public void setMovieRepository(UserRateRepository userRateRepository) {
        this.userRateRepository = userRateRepository;
    }

    @Autowired
    public void setOpenMovieService(OpenMovieApiService openMovieApiService) {
        this.openMovieApiService = openMovieApiService;
    }

    @Autowired
    public void setOscarCandidateService(OscarCandidateService oscarCandidateService) {
        this.oscarCandidateService = oscarCandidateService;
    }

    public ApiResponseDto checkMovieWonState(String title) {
        ResponseEntity<OpenMovieResponseDto> omdResponse = openMovieApiService.getMovieInfo(title);

        if (omdResponse.getStatusCode().is2xxSuccessful()) {

            OpenMovieResponseDto apiResponse = omdResponse.getBody();
            Optional.ofNullable(apiResponse).orElseThrow(ApiEmptyResultException::new);

            return handleApiResponseAndCheckIfWon(apiResponse);
        }

        throw new ApiCommunicationException();
    }

    private ApiResponseDto handleApiResponseAndCheckIfWon(OpenMovieResponseDto apiResponse) {
        ApiResponseDto.Builder apiResponseBuilder = ApiResponseDto.builder();

        if (isBusinessSuccess(apiResponse)) {

            Boolean isWon = oscarCandidateService.isWon(apiResponse.getTitle());
            Movie movie = Movie.builder().from(apiResponse).won(isWon).build();
            apiResponseBuilder.result(movie).ok();

        } else {
            throw new OMDBBusinessException(apiResponse.getError());
        }

        return apiResponseBuilder.build();
    }

    private Boolean isBusinessSuccess(OpenMovieResponseDto apiResponse) {
        return Optional.ofNullable(apiResponse).isPresent() &&
                Optional.ofNullable(apiResponse.getResponse()).map(r -> r.equalsIgnoreCase("True")).orElse(false);
    }

    public ApiResponseDto rate(RateRequestDto rateRequestDto, String user) {
        ResponseEntity<OpenMovieResponseDto> omdResponse = openMovieApiService.getMovieInfo(rateRequestDto.getTitle());

        if (omdResponse.getStatusCode().is2xxSuccessful()) {

            OpenMovieResponseDto apiResponse = omdResponse.getBody();
            Optional.ofNullable(apiResponse).orElseThrow(ApiEmptyResultException::new);

            if (!isBusinessSuccess(apiResponse)) {
                throw new OMDBBusinessException(apiResponse.getError());
            }
            if (isDuplicateRate(rateRequestDto, user)) {
                throw new UserDuplicateRateException();
            }

            UserRate userRate = UserRate.builder()
                    .from(rateRequestDto)
                    .user(user)
                    .boxOffice(apiResponse.getBoxOffice())
                    .build();
            userRateRepository.save(userRate);
            return ApiResponseDto.builder().created().build();
        }

        throw new ApiCommunicationException();
    }

    public ApiResponseDto getTopTen(Integer page) {
        int start = page * 10;
        List<Object[]> tuples = userRateRepository.topTenMovieOrderedByBoxOffice(PageRequest.of(start, start + 10));

        List<TopRateMovieDto> topRateMovies = tuples.stream()
                .map(
                        m -> TopRateMovieDto.builder().from(m).build()
                ).collect(Collectors.toList());
        return ApiResponseDto.builder().result(topRateMovies).ok().build();
    }

    private Boolean isDuplicateRate(RateRequestDto rateRequestDto, String user) {
        return userRateRepository.findByMovieTitleAndAndUser(rateRequestDto.getTitle(), user).isPresent();
    }
}
