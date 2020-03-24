package com.example.demo.dao;

import com.example.demo.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class ExpenseDataAccessService implements ExpenseDao {

    @Override
    public int insertExpense(UUID id, Expense expense) {
        return 0;
    }

    @Override
    public List<Expense> selectAllExpenses() {
        return List.of(new Expense(UUID.randomUUID(), "Random Expense from POSTGRES DB"));
    }

    @Override
    public Optional<Expense> selectExpenseById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deleteExpenseById(UUID id) {
        return 0;
    }

    @Override
    public int updateExpenseById(UUID id, Expense expense) {
        return 0;
    }
}
