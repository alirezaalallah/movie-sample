package com.example.moviesample.dao.model;

import com.example.moviesample.dto.api.RateRequestDto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_rates")
public class UserRate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_Id")
    private Long uniqueId;

    @Basic
    @Column(name = "user")
    private String user;

    @Basic
    @Column(name = "value")
    private Integer value;

    @Basic
    @Column(name = "movie_title")
    private String movieTitle;

    @Basic
    @Column(name = "box_office")
    private BigDecimal boxOffice;

    @Basic
    @Column(name = "created")
    private LocalDateTime create;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BigDecimal boxOffice) {
        this.boxOffice = boxOffice;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    @PrePersist
    public void prePersist() {
        this.create = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRate userRate = (UserRate) o;
        return Objects.equals(uniqueId, userRate.uniqueId) && Objects.equals(user, userRate.user) && Objects.equals(value, userRate.value) && Objects.equals(movieTitle, userRate.movieTitle) && Objects.equals(boxOffice, userRate.boxOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, user, value, movieTitle, boxOffice);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UserRate userRate = new UserRate();

        public Builder from(RateRequestDto rateRequestDto) {
            userRate.value = Integer.parseInt(rateRequestDto.getValue());
            userRate.movieTitle = rateRequestDto.getTitle();

            return this;
        }

        public Builder user(String user) {
            userRate.user = user;
            return this;
        }

        public Builder value(Integer value) {
            userRate.value = value;
            return this;
        }

        public Builder movieTitle(String movieTitle) {
            userRate.movieTitle = movieTitle;
            return this;
        }

        public Builder boxOffice(BigDecimal boxOffice) {
            userRate.boxOffice = boxOffice;
            return this;
        }

        public UserRate build() {
            return userRate;
        }

    }
}
