package com.vojvoda.ecomerceapi.exceptions.models;

import com.vojvoda.ecomerceapi.exceptions.ApiException;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message);

    }
}
