package com.example.demo.api;

import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/expense")
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public void addExpense(@RequestBody Expense expense) {
        expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> selectAllExpenses() {
        return expenseService.selectAllExpenses();
    }
}
