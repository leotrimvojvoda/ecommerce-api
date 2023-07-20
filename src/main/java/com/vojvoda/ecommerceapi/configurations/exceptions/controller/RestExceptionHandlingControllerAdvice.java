package com.vojvoda.ecommerceapi.configurations.exceptions.controller;

import com.vojvoda.ecommerceapi.configurations.exceptions.models.*;
import com.vojvoda.ecommerceapi.configurations.security.authority.AuthorityEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;


/**
 * RestExceptionHandlingController class is used to intercept all thrown messages and return them
 * to format. Each method has the name of the error that it is supposed to handle.
 */
@ControllerAdvice
@Log4j2
public class RestExceptionHandlingControllerAdvice {

    private final HttpServletRequest request;
    private final MessageSource messageSource;

    public RestExceptionHandlingControllerAdvice(HttpServletRequest request,
                                                 MessageSource messageSource) {
        this.request = request;
        this.messageSource = messageSource;
    }

    /**
     * This method is responsible for building a ResponseEntity of the Exception that is to be
     * thrown and to log the exceptions' stack trace to the console.
     *
     * @param exception               - This parameter is used to print the error stack trace and the
     *                                error message to the console.
     * @param exceptionResponseObject - This parameter is passed to the ResponseEntity that is
     *                                being prepared to be returned to the client.
     */
    private ResponseEntity<Object> buildResponseEntity(
            Exception exception, ExceptionResponseObject exceptionResponseObject) {

        exceptionResponseObject.setPath(request.getRequestURI());

        if (exception != null) {
            exception.printStackTrace();
            log.error("Error Message: {}", exception.getMessage());
        }

        return new ResponseEntity<>(exceptionResponseObject, exceptionResponseObject.getStatus());
    }

    /**
     * In case invalid data is sent from the client a MethodArgumentNotValidException will be
     * thrown in which case we intercept that exception and filter out the details in order to
     * let the client know what data was invalid in the request.
     *
     * @param exception - Takes a {@link MethodArgumentNotValidException} object that is used
     *                  to extract error details about the invalid data that was entered.
     * @param locale    - Takes a {@link Locale} object that is used to be passed in
     *                  {@link MessageSource#getMessage(MessageSourceResolvable, Locale)}
     * @return {@link ResponseEntity} with a status code BAD_REQUEST, a "Validation Errors" message and the
    {@link ValidationError} dto with detailed information about the invalid data.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseObject> handleValidationErrors(MethodArgumentNotValidException exception,
                                                                          Locale locale) {
        String methodName = "handlerValidateException";

        log.error("{} -> Error fields: {}", methodName, exception.getCause(), exception);

        List<ValidationError> validationErrors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String object = error.getObjectName();
            String field = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String errorMessage;

            try {
                errorMessage = messageSource.getMessage(
                        (Objects.requireNonNull(error.getDefaultMessage())), null, locale);
            } catch (Exception e) {
                errorMessage = error.getDefaultMessage();
            }

            validationErrors.add(new ValidationError(object, field, rejectedValue, errorMessage));

            log.error("Validation Error:"
                            + " Path {},"
                            + " Object {},"
                            + " Field {}, "
                            + " Rejected Value {},"
                            + " Message: {}",
                    request.getRequestURI(), object, field, rejectedValue, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseObject(HttpStatus.BAD_REQUEST, "Validation Errors", validationErrors));
    }

    /**
     * This method is responsible to intercept and return to the client
     * all Bad Request (400) exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(),
                        exception.getValidationErrors());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Conflict (409) exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.CONFLICT, exception.getMessage(), exception.getValidationErrors());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Not Found (404) exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleEndpointNotFoundException(NoHandlerFoundException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.NOT_FOUND, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Not Found (404) exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.NOT_FOUND, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Internal Server Error (500)
     * exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception exception) {
        if (exception instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible for intercepting and handling Unauthorized (401)
     *       exceptions that are thrown.
     *
     * @param ex - Used to get details about the exception.
     * @return ResponseEntity of {@link ExceptionResponseObject}.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String message = ex.getLocalizedMessage();

        if (ex.getLocalizedMessage().equals("Full authentication is required to access this resource")) {
            httpStatus = HttpStatus.FORBIDDEN;
            message = "Missing Credentials!";
        }

        ExceptionResponseObject errorResponse = new ExceptionResponseObject(httpStatus,
                "AUTHENTICATION FAILED: " + message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * This method is responsible to intercept and return to the client all Access Denied / Forbidden (403)
     * exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all
     * Access Denied / Forbidden (403) exceptions that are thrown and includes
     * additional information about the condition of the token.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleInvalidToken(ExpiredJwtException exception) {
        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Access Denied / Forbidden (403)
     * exceptions that are thrown and is thrown in case of a malformed jwt token.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleInvalidToken(MalformedJwtException exception) {
        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.FORBIDDEN, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible for intercepting and handling HttpRequestMethodNotSupportedException
     * exceptions that occur when an HTTP request method is not supported, e.g. out endpoint is GET, and
     * it gets called with a POST method.
     *
     * @param exception - Used to get details about the exception.
     * @return ResponseEntity of a string message and HTTP status code NOT_IMPLEMENTED (501).
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Data Access Exceptions
     * or SQL exceptions that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");

        return buildResponseEntity(exception, exceptionResponseObject);
    }


    /**
     * This method is responsible to intercept and return to the client errors that are thrown by entering a
     * non-existent Role when creating or updating a user.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleDataAccessException(HttpMessageNotReadableException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.BAD_REQUEST, "Role must be of type: "
                        + EnumSet.allOf(AuthorityEnum.class));

        return buildResponseEntity(exception, exceptionResponseObject);
    }

    /**
     * This method is responsible to intercept and return to the client all Tenant configuration Exceptions
     * that are thrown.
     *
     * @param exception - Used to get details about the exception
     * @return ResponseEntity of {@link ExceptionResponseObject}
     */
    @ExceptionHandler(TenantException.class)
    public ResponseEntity<Object> handleTenantException(TenantException exception) {

        ExceptionResponseObject exceptionResponseObject =
                new ExceptionResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return buildResponseEntity(exception, exceptionResponseObject);
    }
}