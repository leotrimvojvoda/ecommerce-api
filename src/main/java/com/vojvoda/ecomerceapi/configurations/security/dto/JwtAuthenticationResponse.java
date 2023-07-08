package com.vojvoda.ecomerceapi.configurations.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponse {

    private String token;
}
