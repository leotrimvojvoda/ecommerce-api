package com.vojvoda.ecomerceapi.configurations.security.services.authentication;

import com.vojvoda.ecomerceapi.configurations.security.SecurityUser;
import com.vojvoda.ecomerceapi.configurations.security.authority.Authority;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityNotFoundException;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityRepository;
import com.vojvoda.ecomerceapi.configurations.security.dto.JwtAuthenticationResponse;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignInRequest;
import com.vojvoda.ecomerceapi.configurations.security.dto.SignUpRequest;
import com.vojvoda.ecomerceapi.configurations.security.services.database.JpaUserDetailsService;
import com.vojvoda.ecomerceapi.configurations.security.services.jwt.JwtService;
import com.vojvoda.ecomerceapi.core.user.User;
import com.vojvoda.ecomerceapi.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final JpaUserDetailsService jpaUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        Authority authorities = authorityRepository.getAuthorityByName(request.getRole())
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found: " + request.getRole()));

        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(Set.of(authorities));

        userRepository.save(user);
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
