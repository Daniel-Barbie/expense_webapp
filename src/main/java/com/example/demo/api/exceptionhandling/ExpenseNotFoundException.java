package com.example.demo.api.exceptionhandling;

import com.example.demo.api.LoggingController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class ExpenseNotFoundException extends RuntimeException{
    private static final Logger LOGGER = LogManager.getLogger(LoggingController.class);

    /**
     * Exception to be thrown when a single Expense is queried for that does not exist
     *
     * @param id the Expense id
     */
    public ExpenseNotFoundException(UUID id) {
        /*
        TL;DR:
        super() calls the Parent class constructor. Here it passes the error message to a new RuntimeException Object

        super(param) calls the Parent class constructor and passes the parameter
        in this case, RuntimeException.java has a constructor that takes a String as a parameter which is called "message"
        -> so the string we pass the super constructor is the Exception message that our CustomGlobalExceptionHandler
        uses to pass a text response in JSON format to the API consumer
         */
        super("Expense ID not found: " + id.toString());
        LOGGER.debug("A client tried to access a nonexistent Expense with ID " + id);
    }

}
