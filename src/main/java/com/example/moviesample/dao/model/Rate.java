package com.example.moviesample.dao.model;

import com.example.moviesample.dto.RateDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uniqueId;
    private String source;
    private String value;
    private Movie movie;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(uniqueId, rate.uniqueId) && Objects.equals(source, rate.source)
                && Objects.equals(value, rate.value) && Objects.equals(movie, rate.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, source, value, movie);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Rate rate = new Rate();

        public Builder from(RateDto rateDto) {
            rate.source = rateDto.getSource();
            rate.value = rateDto.getValue();
            return this;
        }

        public Builder source(String source) {
            rate.source = source;
            return this;
        }

        public Builder value(String value) {
            rate.value = value;
            return this;
        }

        public Rate build() {
            return rate;
        }
    }
}
