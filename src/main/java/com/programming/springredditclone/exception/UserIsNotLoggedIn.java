package com.programming.springredditclone.exception;

public class UserIsNotLoggedIn extends RuntimeException {
    public UserIsNotLoggedIn(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public UserIsNotLoggedIn(String msg) {
        super(msg);
    }

}
