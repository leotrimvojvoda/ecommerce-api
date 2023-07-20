package com.vojvoda.ecommerceapi.configurations.validations.unique_username;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This validation annotation is used on field or parameter level to check whether and username
 * or email exists in the database.
 * The logic for this annotation is implemented in {@link UsernameConstraintValidator}
 * */
@Constraint(validatedBy = UsernameConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "This username is taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}