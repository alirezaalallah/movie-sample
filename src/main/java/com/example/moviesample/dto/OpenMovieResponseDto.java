package com.example.moviesample.dto;

import com.example.moviesample.dao.model.MovieType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMovieResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    @JsonDeserialize(using = DateSerializer.class)
    private LocalDate released;

    @JsonProperty("Runtime")
    @JsonDeserialize(using = MovieRuntimeSerializer.class)
    private Integer runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Ratings")
    private List<RateDto> ratings;

    @JsonProperty("Metascore")
    private String metaScore;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private MovieType type;

    @JsonProperty("BoxOffice")
    @JsonDeserialize(using = BoxOfficeSerializer.class)
    private BigDecimal boxOffice;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<RateDto> getRatings() {
        return ratings;
    }

    public void setRatings(List<RateDto> ratings) {
        this.ratings = ratings;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BigDecimal boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenMovieResponseDto that = (OpenMovieResponseDto) o;
        return Objects.equals(title, that.title) && Objects.equals(year, that.year) && Objects.equals(rated, that.rated) && Objects.equals(released, that.released) && Objects.equals(runtime, that.runtime) && Objects.equals(genre, that.genre) && Objects.equals(director, that.director) && Objects.equals(writer, that.writer) && Objects.equals(actors, that.actors) && Objects.equals(plot, that.plot) && Objects.equals(language, that.language) && Objects.equals(country, that.country) && Objects.equals(awards, that.awards) && Objects.equals(poster, that.poster) && Objects.equals(ratings, that.ratings) && Objects.equals(metaScore, that.metaScore) && Objects.equals(imdbRating, that.imdbRating) && Objects.equals(imdbVotes, that.imdbVotes) && Objects.equals(imdbID, that.imdbID) && type == that.type && Objects.equals(boxOffice, that.boxOffice) && Objects.equals(response, that.response) && Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, poster, ratings, metaScore, imdbRating, imdbVotes, imdbID, type, boxOffice, response, error);
    }
}
