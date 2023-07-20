package com.vojvoda.ecommerceapi.configurations.exceptions.models;

import com.vojvoda.ecommerceapi.configurations.exceptions.ApiException;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message);

    }
}
