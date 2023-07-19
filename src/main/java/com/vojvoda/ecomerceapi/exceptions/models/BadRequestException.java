package com.vojvoda.ecomerceapi.exceptions.models;

import com.vojvoda.ecomerceapi.exceptions.ApiException;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }
}

