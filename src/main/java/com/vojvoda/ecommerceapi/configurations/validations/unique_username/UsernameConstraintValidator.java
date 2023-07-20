package com.vojvoda.ecommerceapi.configurations.validations.unique_username;



import com.vojvoda.ecommerceapi.core.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;



/**
 * This class implements {@link ConstraintValidator} that is part of java bean validation and is used to implement
 * custom validations. This class provides te logic that is used to determine if a username/email is valid or not.
 * */
@Component
@RequiredArgsConstructor
public class UsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    /**
     * Checks whether the given username/email exists by calling a query that returns a bool value.
     * If the username/email exists, it means that the given value is not valid so this method would return false. That
     * would throw an {@link MethodArgumentNotValidException} and the error message would be returned.
     *
     * @return {@link Boolean} false (invalid) if the user exists, true (valid) if the user does not exist.
     * */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsUserByEmail(email);
    }
}
