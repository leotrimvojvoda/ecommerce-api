package com.vojvoda.ecomerceapi.exceptions;


import com.vojvoda.ecomerceapi.exceptions.models.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


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
