package com.programming.springredditclone.exception;

public class SpringRedditNotFoundException extends RuntimeException {
    public SpringRedditNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditNotFoundException(String msg) {
        super(msg);
    }
}
