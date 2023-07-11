package com.vojvoda.ecomerceapi.configurations.security.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails, String tenant);

    String extractTenant(String token);
}
