package com.vojvoda.ecomerceapi.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ValidationError object is returned to the client to show details of invalid data that was submitted.
 * */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ValidationError {

    private String object;

    private String field;

    private Object rejectedValue;
    
    private String message;
}
