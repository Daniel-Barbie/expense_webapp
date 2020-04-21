package com.example.demo.service;

import com.example.demo.dao.ExpenseDao;
import com.example.demo.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseDao expenseDao;

    @Autowired
    public ExpenseService(@Qualifier("postgres")ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public int addExpense(Expense expense) {
        return expenseDao.insertExpense(expense);
    }

    public List<Expense> selectAllExpenses() {
        return expenseDao.selectAllExpenses();
    }

    public Expense selectExpenseById(UUID id) {
        return expenseDao.selectExpenseById(id);
    }

    public int deleteExpense(UUID id) {
        return expenseDao.deleteExpenseById(id);
    }

    public int updateExpense(UUID id, Expense newExpense) {
        return expenseDao.updateExpenseById(id, newExpense);
    }

}
