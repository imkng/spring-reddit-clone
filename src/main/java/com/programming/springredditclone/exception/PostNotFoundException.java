package com.programming.springredditclone.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public PostNotFoundException(String msg) {
        super(msg);
    }
}
