package com.vojvoda.ecomerceapi.exceptions.models;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
