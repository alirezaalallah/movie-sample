package com.example.moviesample.dto.api;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RateRequestDto implements Serializable {
    private static final long serialVersionUID = 1234567L;

    @NotNull(message = "title is required")
    private String title;

    @NotNull(message = "value is required")
    @Range(min = 0, max = 10, message = "value should be between 0 and 10")
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RateRequestDto rateRequestDto = new RateRequestDto();

        public Builder title(String title) {
            rateRequestDto.title = title;
            return this;
        }

        public Builder value(String value) {
            rateRequestDto.value = value;
            return this;
        }

        public RateRequestDto build() {
            return rateRequestDto;
        }
    }
}
