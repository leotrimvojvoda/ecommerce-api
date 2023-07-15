package com.vojvoda.ecomerceapi.configurations.security.services.authentication;

import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.configurations.security.services.database.JpaUserDetailsService;
import com.vojvoda.ecomerceapi.configurations.security.services.jwt.JwtService;
import com.vojvoda.ecomerceapi.configurations.security.user.SecurityUser;
import com.vojvoda.ecomerceapi.core.user.User;
import com.vojvoda.ecomerceapi.core.user.UserService;
import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JpaUserDetailsService jpaUserDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Override
    public JwtAuthenticationResponse signup(CreateUser createUser) {
        User user = userService.createUser(createUser);

        var jwt = jwtService.generateToken(new SecurityUser(user));

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = jpaUserDetailsService.loadUserByUsername(request.getEmail());
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
