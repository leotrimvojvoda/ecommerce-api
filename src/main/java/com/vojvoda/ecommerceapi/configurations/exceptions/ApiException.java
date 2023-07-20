package com.vojvoda.ecommerceapi.configurations.exceptions;


import com.vojvoda.ecommerceapi.configurations.exceptions.models.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The API exception serves as a filter between a given exception and runtime exception.
 * This is done to filter out the rest of the RuntimeException fields that our exception class would automatically
 * extend, and it adds validation errors list.
 * */

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private String message;
    private List<ValidationError> validationErrors;

    public ApiException(String message) {
        this.message = message;
    }
}
