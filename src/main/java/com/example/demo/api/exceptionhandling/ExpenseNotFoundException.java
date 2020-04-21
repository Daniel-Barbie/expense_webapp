package com.example.demo.api.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND)  //404
public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException(UUID id) {
        super("Expense ID not found: " + id.toString());
    }

}
