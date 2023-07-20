package com.vojvoda.ecommerceapi.configurations.exceptions.models;

import com.vojvoda.ecommerceapi.configurations.exceptions.ApiException;

public class TenantException extends ApiException {
    public TenantException(String message) {
        super(message);

    }
}
