package com.example.moviesample.exception;

public class UserDuplicateRateException extends RuntimeException {
    public UserDuplicateRateException() {
        super("User has rated before");
    }
}
