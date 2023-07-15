package com.vojvoda.ecomerceapi.configurations.security.controller;

import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.configurations.security.services.authentication.AuthenticationService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid CreateUser createUser) {
        return ResponseEntity.ok(authenticationService.signup(createUser));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
