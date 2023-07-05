package com.vojvoda.ecomerceapi.configurations.security.services.authentication;

import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}