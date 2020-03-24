package com.example.demo.service;

import com.example.demo.dao.ExpenseDao;
import com.example.demo.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseDao expenseDao;

    @Autowired
    public ExpenseService(@Qualifier("fakeDao")ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public int addExpense(Expense expense) {
        return expenseDao.insertExpense(expense);
    }
}
