package com.vojvoda.ecomerceapi.configurations.security.controller;

import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignUpRequest;
import com.vojvoda.ecomerceapi.configurations.security.services.authentication.AuthenticationService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
