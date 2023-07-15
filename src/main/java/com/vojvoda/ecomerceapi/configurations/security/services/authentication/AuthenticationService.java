package com.vojvoda.ecomerceapi.configurations.security.services.authentication;

import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(CreateUser request);

    JwtAuthenticationResponse signIn(SignInRequest request);

}