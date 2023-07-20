package com.vojvoda.ecommerceapi.configurations.exceptions.models;

import com.vojvoda.ecommerceapi.configurations.exceptions.ApiException;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }
}

