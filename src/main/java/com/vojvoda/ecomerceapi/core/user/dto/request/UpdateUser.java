package com.vojvoda.ecomerceapi.core.user.dto.request;

import com.vojvoda.ecomerceapi.core.user.User;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    @Positive
    private int id;

    private String firstName;

    private String lastName;

    private String password;

}
