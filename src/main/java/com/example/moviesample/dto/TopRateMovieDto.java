package com.example.moviesample.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TopRateMovieDto implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private String name;
    private double rate;
    private BigDecimal boxOffice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BigDecimal boxOffice) {
        this.boxOffice = boxOffice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopRateMovieDto that = (TopRateMovieDto) o;
        return Double.compare(that.rate, rate) == 0 && Objects.equals(name, that.name) && Objects.equals(boxOffice, that.boxOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate, boxOffice);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TopRateMovieDto topRateMovieDto = new TopRateMovieDto();

        public Builder from(Object[] tuple) {
            topRateMovieDto.name = (String) tuple[0];
            topRateMovieDto.boxOffice = (BigDecimal) tuple[1];
            topRateMovieDto.rate = (double) tuple[2];

            return this;
        }

        public Builder name(String name) {
            topRateMovieDto.name = name;
            return this;
        }

        public Builder rate(double rate) {
            topRateMovieDto.rate = rate;
            return this;
        }

        public Builder boxOffice(BigDecimal boxOffice) {
            topRateMovieDto.boxOffice = boxOffice;
            return this;
        }

        public TopRateMovieDto build() {
            return topRateMovieDto;
        }
    }
}
