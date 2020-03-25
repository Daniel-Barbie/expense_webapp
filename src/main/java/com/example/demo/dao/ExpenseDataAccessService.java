package com.example.demo.dao;

import com.example.demo.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class ExpenseDataAccessService implements ExpenseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT ONE
    @Override
    public int insertExpense(UUID id, Expense expense) {
        final String sql = "INSERT INTO expense (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, expense.getName());
        return 1;
    }

    // SELECT ALL
    @Override
    public List<Expense> selectAllExpenses() {
        final String sql = "SELECT id, name FROM expense";
        // the following returns a List<Expense>
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Expense(id, name);
        });
    }

    // SELECT ONE
    @Override
    public Optional<Expense> selectExpenseById(UUID id) {
        final String sql = "SELECT id, name FROM expense WHERE id = ?";
        Expense expenseById = jdbcTemplate.queryForObject(
                sql,
                //additional arguments may be passed:
                new Object[]{id},
                (resultSet, i) -> {
                    UUID expenseId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Expense(expenseId, name);
                });
        return Optional.ofNullable(expenseById);
    }

    // DELETE ONE
    @Override
    public int deleteExpenseById(UUID id) {
        final String sql = "DELETE FROM expense WHERE id=?";
        jdbcTemplate.update(sql, id);
        return 1;
    }

    // UPDATE ONE
    @Override
    public int updateExpenseById(UUID id, Expense expense) {

        final String sql = "UPDATE expense SET name = ? WHERE id=?";
        jdbcTemplate.update(sql, expense.getName(), id);
        return 1;

        // ALTERNATIVE APPROACH: (note the use of single quotes '' for parameter values)
/*
        final String sql = "UPDATE expense SET name='"+expense.getName()+"' WHERE id='"+id+"'";
        jdbcTemplate.update(sql);
*/
    }
}
