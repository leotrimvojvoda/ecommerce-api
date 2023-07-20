package com.vojvoda.ecommerceapi.core.user.dto.request;

import com.vojvoda.ecommerceapi.configurations.security.authority.AuthorityEnum;
import com.vojvoda.ecommerceapi.configurations.validations.unique_username.UniqueUsername;
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

        @NotNull(message = "Email not present")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Must me of type email")
        @UniqueUsername(message = "Email already exists")
        String email;

        @Size(min = 3, max = 18, message = "Password must be between 3 and 18 characters long")
        String password;

        @NotNull(message = "Authority cannot be null")
        AuthorityEnum authority;
}
