package com.example.demo.api;

import com.example.demo.api.exceptionhandling.ExpenseNotFoundException;
import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/v1/expense")
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public void addExpense(@Valid @NonNull @RequestBody Expense expense) {
        expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> selectAllExpenses() {
        return expenseService.selectAllExpenses();
    }

    @GetMapping(path = "{id}")
    public Expense selectExpenseById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(expenseService.selectExpenseById(id))
                .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @DeleteMapping(path = "{id}")
    public void deleteExpenseById(@PathVariable("id") UUID id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping(path = "{id}")
    public void updateExpense(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Expense expenseToUpdate) {
        expenseService.updateExpense(id, expenseToUpdate);
    }
}
