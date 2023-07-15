package com.vojvoda.ecomerceapi.core.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    private Long id;

    private String firstName;

    private String lastName;

    private String password;
}
