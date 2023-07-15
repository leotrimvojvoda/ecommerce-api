package com.vojvoda.ecomerceapi.core.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewUser {

    private long id;

    private String firstName;

    private String lastName;

    private String email;
}
