package com.vojvoda.ecomerceapi.core.user.dto.request;

import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser{

        @NotBlank(message = "First name can not be blank")
        String firstName;

        @NotBlank(message = "Last name can not be blank")
        String lastName;

        @NotNull(message = "Email can not be null")
        @Email(message = "Must me of type email")
        @NotBlank(message = "Email can not be blank")
        String email;

        @Size(min = 3, max = 18, message = "Password must be between 3 and 18 characters long")
        String password;

        @NotNull(message = "Authority cannot be null")
        @NotBlank(message = "Authority cannot be blank")
        AuthorityEnum authority;
}
