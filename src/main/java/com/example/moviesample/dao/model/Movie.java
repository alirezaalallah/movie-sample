package com.example.moviesample.dao.model;

import com.example.moviesample.dto.OpenMovieResponseDto;
import com.example.moviesample.dto.RateDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uniqueId;
    private String title;
    private String year;
    private String rated;
    private LocalDate released;
    private Integer runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private List<Rate> ratings;
    private String metaScore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private MovieType type;
    private BigDecimal boxOffice;
    private Boolean won;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

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

    public List<Rate> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rate> ratings) {
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

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(uniqueId, movie.uniqueId) && Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(rated, movie.rated) && Objects.equals(released, movie.released) && Objects.equals(runtime, movie.runtime) && Objects.equals(genre, movie.genre) && Objects.equals(director, movie.director) && Objects.equals(writer, movie.writer) && Objects.equals(actors, movie.actors) && Objects.equals(plot, movie.plot) && Objects.equals(language, movie.language) && Objects.equals(country, movie.country) && Objects.equals(awards, movie.awards) && Objects.equals(poster, movie.poster) && Objects.equals(ratings, movie.ratings) && Objects.equals(metaScore, movie.metaScore) && Objects.equals(imdbRating, movie.imdbRating) && Objects.equals(imdbVotes, movie.imdbVotes) && Objects.equals(imdbID, movie.imdbID) && type == movie.type && Objects.equals(boxOffice, movie.boxOffice) && Objects.equals(won, movie.won);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, title, year, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, poster, ratings, metaScore, imdbRating, imdbVotes, imdbID, type, boxOffice, won);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Movie movie = new Movie();

        public Builder from(OpenMovieResponseDto response) {
            movie.title = response.getTitle();
            movie.year = response.getYear();
            movie.rated = response.getRated();
            movie.released = response.getReleased();
            movie.runtime = response.getRuntime();
            movie.genre = response.getGenre();
            movie.director = response.getDirector();
            movie.writer = response.getWriter();
            movie.actors = response.getActors();
            movie.plot = response.getPlot();
            movie.language = response.getLanguage();
            movie.country = response.getCountry();
            movie.awards = response.getAwards();
            movie.poster = response.getPoster();
            movie.ratings = toRateList(response.getRatings());
            movie.metaScore = response.getMetaScore();
            movie.imdbRating = response.getImdbRating();
            movie.imdbVotes = response.getImdbVotes();
            movie.imdbID = response.getImdbID();
            movie.type = response.getType();

            movie.boxOffice = response.getBoxOffice();
            return this;
        }

        public Builder won(Boolean won) {
            movie.won = won;
            return this;
        }

        public Builder title(String title) {
            movie.title = title;
            return this;
        }

        public Builder year(String year) {
            movie.year = year;
            return this;
        }

        public Builder rated(String rated) {
            movie.rated = rated;
            return this;
        }

        public Builder released(LocalDate released) {
            movie.released = released;
            return this;
        }

        public Builder runtime(Integer runtime) {
            movie.runtime = runtime;
            return this;
        }

        public Builder genre(String genre) {
            movie.genre = genre;
            return this;
        }

        public Builder director(String director) {
            movie.director = director;
            return this;
        }

        public Builder writer(String writer) {
            movie.writer = writer;
            return this;
        }

        public Builder actors(String actors) {
            movie.actors = actors;
            return this;
        }

        public Builder plot(String plot) {
            movie.plot = plot;
            return this;
        }

        public Builder language(String language) {
            movie.language = language;
            return this;
        }

        public Builder country(String country) {
            movie.country = country;
            return this;
        }

        public Builder awards(String awards) {
            movie.awards = awards;
            return this;
        }

        public Builder poster(String poster) {
            movie.poster = poster;
            return this;
        }

        public Builder ratings(List<Rate> ratings) {
            movie.ratings = ratings;
            return this;
        }

        public Builder metaScore(String metaScore) {
            movie.metaScore = metaScore;
            return this;
        }

        public Builder imdbRating(String imdbRating) {
            movie.imdbRating = imdbRating;
            return this;
        }

        public Builder imdbVotes(String imdbVotes) {
            movie.imdbVotes = imdbVotes;
            return this;
        }

        public Builder imdbID(String imdbID) {
            movie.imdbID = imdbID;
            return this;
        }

        public Builder type(MovieType type) {
            movie.type = type;
            return this;
        }

        public Builder boxOffice(BigDecimal boxOffice) {
            movie.boxOffice = boxOffice;
            return this;
        }

        private List<Rate> toRateList(List<RateDto> rateDtoList) {
            return rateDtoList.stream()
                    .map(
                            rateDto -> Rate.builder().from(rateDto).build()
                    ).collect(Collectors.toList());
        }

        public Movie build() {
            return movie;
        }

    }
}
