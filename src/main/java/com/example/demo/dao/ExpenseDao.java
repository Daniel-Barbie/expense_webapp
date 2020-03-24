package com.example.demo.dao;

import com.example.demo.model.Expense;

import java.util.UUID;

public interface ExpenseDao {

        int insertExpense(UUID id, Expense expense);

        default int insertExpense(Expense expense) {
            UUID id = UUID.randomUUID();
            return insertExpense(id, expense);
        }
}
