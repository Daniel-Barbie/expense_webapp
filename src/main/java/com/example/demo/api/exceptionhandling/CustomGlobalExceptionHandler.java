
package com.example.demo.api.exceptionhandling;

import com.example.demo.api.LoggingController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

/*
see https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
and http://zetcode.com/springboot/controlleradvice/
as sources for the code
 */

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(LoggingController.class);

    /**
     * Provides handling for exceptions throughout this service.
     *
     * @param ex The target exception
     * @param request The current request
     */
    @ExceptionHandler(value
            = { ExpenseNotFoundException.class})

    public final ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();

        /*
        custom handling for each custom exception
         */
        if (ex instanceof ExpenseNotFoundException) {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;   //404
            ExpenseNotFoundException enfe = (ExpenseNotFoundException) ex;

            // use central response body customization for api exceptions
            return handleExpenseNotFoundException(enfe, httpHeaders, httpStatus, request);
        }else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Unknown Exception type: " + ex.getClass().getName());
            }
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, httpHeaders, httpStatus, request);
        }
    }

    /**
     * Customize the response for UserNotFoundException.
     *
     * @param ex The exception
     * @param httpHeaders The headers to be written to the response
     * @param httpStatus The selected response status
     * @return a {@code ResponseEntity} instance
     */
    // what is a singletonList?
    protected ResponseEntity<Object> handleExpenseNotFoundException(ExpenseNotFoundException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, errors, httpHeaders, httpStatus, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, List<String> body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

}
