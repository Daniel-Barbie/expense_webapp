package com.example.demo.dao;

import com.example.demo.api.LoggingController;
import com.example.demo.model.Expense;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Barbie
 */
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

    /**
     * Inserts a new Expense into the DB. Not to be called directly by a service, only via the default
     * {@link com.example.demo.dao.ExpenseDao#insertExpense(Expense)} method.
     *
     * @param id A newly generated Expense id (UUID)
     * @param expense The Expense
     * @return {@code 1} - When finished
     */
    /*
     * it may be smart to try and make this method "hidden" except for the method insertExpense(Expense) which is
     * defined as a default in the Interface ExpenseDao
     */
    // INSERT ONE
    @Override
    public int insertExpense(UUID id, Expense expense) {
        final String sql = "INSERT INTO expense (id, name, amount, userid, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, expense.getName(), expense.getAmount(), expense.getUserid(), expense.getDate());
        return 1;
    }

    /**
     * Queries for all Expenses
     *
     * @return {@code List<Expense>} - that includes all saved Expenses
     */
    /*
    maybe a map / hasmap / linkedhasmap makes more sense here?
     */
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

    /**
     * Queries for a single Expense
     *
     * @param id The Expense id
     * @return {@code Expense} or {@code null} - if the Expense id is not present in the DB
     */
    // SELECT ONE
    @Override
    public Expense selectExpenseById(UUID id) {
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
            return expenseById;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * Deletes a single Expense from the DB
     *
     * @param id The Expense id
     * @return {@code int} - Number of rows affected (should be 0 or 1)
     */
    // DELETE ONE
    @Override
    public int deleteExpenseById(UUID id) {
        final String sql = "DELETE FROM expense WHERE id=?";
        // returns the number of rows affected
        return jdbcTemplate.update(sql, id);
    }

    /**
     *
     * @param id The Expense id
     * @param expense The updated Expense
     * @return {@code int} - The number of rows affected
     */
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
