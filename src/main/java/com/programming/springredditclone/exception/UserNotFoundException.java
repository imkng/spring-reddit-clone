package com.programming.springredditclone.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
