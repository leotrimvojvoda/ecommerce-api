package com.vojvoda.ecommerceapi.configurations.security.authority;

import org.springframework.security.core.AuthenticationException;

public class AuthorityNotFoundException extends AuthenticationException {

    public AuthorityNotFoundException(String message) {
        super(message);
    }

}
