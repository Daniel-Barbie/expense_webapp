package com.example.demo.dao;

import com.example.demo.api.LoggingController;
import com.example.demo.api.exceptionhandling.ExpenseNotFoundException;
import com.example.demo.model.Expense;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class ExpenseDataAccessService implements ExpenseDao {

    private static final Logger LOGGER = LogManager.getLogger(LoggingController.class);

    // necessary?
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT ONE
    @Override
    public int insertExpense(UUID id, Expense expense) {
        final String sql = "INSERT INTO expense (id, name, amount, userid, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, expense.getName(), expense.getAmount(), expense.getUserid(), expense.getDate());
        return 1;
    }

    // SELECT ALL
    @Override
    public List<Expense> selectAllExpenses() {
        final String sql = "SELECT id, name, amount, userid, date FROM expense";
        // the following returns a List<Expense>
        return jdbcTemplate.query(
                sql,
                (resultSet, i) -> {
                    UUID expenseId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    double amount = Double.valueOf(resultSet.getString("amount"));
                    UUID userid = UUID.fromString(resultSet.getString("userid"));
                    LocalDate date = LocalDate.parse(resultSet.getString("date"));

                    return new Expense(expenseId, name, amount, userid, date);
                });
    }

    // SELECT ONE
    @Override
    public Optional<Expense> selectExpenseById(UUID id) {
        final String sql = "SELECT id, name, amount, userid, date FROM expense WHERE id = ?";
        try {
            Expense expenseById = jdbcTemplate.queryForObject(
                sql,
                //additional arguments may be passed:
                new Object[]{id},
                (resultSet, i) -> {
                    UUID expenseId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    double amount = Double.valueOf(resultSet.getString("amount"));
                    UUID userid = UUID.fromString(resultSet.getString("userid"));
                    LocalDate date = LocalDate.parse(resultSet.getString("date"));

                    return new Expense(expenseId, name, amount, userid, date);
                });
            return Optional.of(expenseById);
        }catch(Exception e){
            throw new ExpenseNotFoundException(id);
        }
    }

    // DELETE ONE
    @Override
    public int deleteExpenseById(UUID id) {
        final String sql = "DELETE FROM expense WHERE id=?";
        // returns the number of rows affected
        return jdbcTemplate.update(sql, id);
    }

    // UPDATE ONE
    @Override
    public int updateExpenseById(UUID id, Expense expense) {

        final String sql = "UPDATE expense SET name = ?, amount = ?, userid = ?, date = ? WHERE id=?";
        // returns the number of rows affected
        return jdbcTemplate.update(sql, expense.getName(), expense.getAmount(), expense.getUserid(), expense.getDate(), id);

        // ALTERNATIVE APPROACH: (note the use of single quotes '' for parameter values)
/*
        final String sql = "UPDATE expense SET name='"+expense.getName()+"' WHERE id='"+id+"'";
        jdbcTemplate.update(sql);
*/
    }
}
