package com.example.demo.dao;

import com.example.demo.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseDao {

    /**
     * Defines insertExpense that takes UUID and Expense. To be overridden by implementations. <br>
     * Is called by the default method {@link #insertExpense(Expense)}. <br>
     * Should never be called directly, only to be called from the {@link #insertExpense(Expense)} default method!
     */
    int insertExpense(UUID id, Expense expense);

    /**
     * Default method to insert an Expense.
     * Defines a new UUID and therefore ensures a new Expense will always have an unused id.
     *
     * @param expense The Expense to be inserted
     * @return {@code int}  - default return value of interface implementation should be 1.
     */
    default int insertExpense(Expense expense) {
        UUID id = UUID.randomUUID();
        return insertExpense(id, expense);
    }

    List<Expense> selectAllExpenses();

    Expense selectExpenseById(UUID id);

    int deleteExpenseById(UUID id);

    int updateExpenseById(UUID id, Expense expense);

}
