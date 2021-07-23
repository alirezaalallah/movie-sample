package com.example.moviesample.exception;

public class ApiEmptyResultException extends RuntimeException {
    public ApiEmptyResultException() {
        super("OMDB API sens empty result");
    }
}