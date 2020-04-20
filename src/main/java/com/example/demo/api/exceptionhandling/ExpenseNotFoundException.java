package com.example.demo.api.exceptionhandling;

import java.util.UUID;

public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException(UUID id) {
        super("Expense ID not found: " + id.toString());
    }

}
