package com.vojvoda.ecommerceapi.configurations.exceptions.models;

import com.vojvoda.ecommerceapi.configurations.exceptions.ApiException;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message);
    }
}
