package com.vojvoda.ecomerceapi.configurations.security.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;

    private String password;

}
