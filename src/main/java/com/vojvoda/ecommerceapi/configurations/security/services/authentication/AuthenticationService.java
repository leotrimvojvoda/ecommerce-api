package com.vojvoda.ecommerceapi.configurations.security.services.authentication;

import com.vojvoda.ecommerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecommerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecommerceapi.core.user.dto.request.CreateUser;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(CreateUser request);

    JwtAuthenticationResponse signIn(SignInRequest request);

}