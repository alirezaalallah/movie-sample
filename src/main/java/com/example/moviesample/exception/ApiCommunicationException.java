package com.example.moviesample.exception;

public class ApiCommunicationException extends RuntimeException {
    public ApiCommunicationException() {
        super("OMDB API has error");
    }
}
