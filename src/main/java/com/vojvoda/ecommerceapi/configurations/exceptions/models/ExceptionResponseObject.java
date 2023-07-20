package com.vojvoda.ecommerceapi.configurations.exceptions.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ExceptionResponseObject is the object has custom fields that will be populated with data that is filtered from the
 * caught exception and provides multiple constructors for different use cases.
 * The {@link ExceptionResponseObject#timestamp} field will be initialized with the date and time when the exception
 * is thrown.
 * The {@link ExceptionResponseObject#status} is initialized with the http status name.
 * The {@link ExceptionResponseObject#message} is initialized with the custom message.
 * The {@link ExceptionResponseObject#path} is initialized with the more specific message that is
 * derived from the exception object.
 * The {@link ExceptionResponseObject#validationErrors} is an object that is created and initialized when invalid data
 * is received from the client.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExceptionResponseObject {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String path;
    private HttpStatus status;
    private String message;
    private List<ValidationError> validationErrors;

    public ExceptionResponseObject() {
        timestamp = LocalDateTime.now();
    }

    public ExceptionResponseObject(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ExceptionResponseObject(HttpStatus status, String message, List<ValidationError> validationErrors) {
        this();
        this.status = status;
        this.message = message;
        this.validationErrors = validationErrors;
    }

}
