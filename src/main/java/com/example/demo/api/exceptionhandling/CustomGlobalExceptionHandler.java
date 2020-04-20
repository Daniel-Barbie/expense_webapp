/*
package com.example.demo.api.exceptionhandling;

import com.example.demo.api.LoggingController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(LoggingController.class);

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class, EmptyResultDataAccessException.class})

    public final ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String bodyOfResponse = "TEST ERROR RESPONSE BODY";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}


*/