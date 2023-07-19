package com.vojvoda.ecomerceapi.exceptions.models;


import com.vojvoda.ecomerceapi.exceptions.ApiException;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message);
    }
}
