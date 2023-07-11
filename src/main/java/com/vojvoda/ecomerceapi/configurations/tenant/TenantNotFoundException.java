package com.vojvoda.ecomerceapi.configurations.tenant;

public class TenantNotFoundException extends RuntimeException{

    public TenantNotFoundException(String message){
        super(message);
    }
}
