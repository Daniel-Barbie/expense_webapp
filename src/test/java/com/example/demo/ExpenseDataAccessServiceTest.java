package com.example.demo;

import com.example.demo.dao.ExpenseDataAccessService;
import com.example.demo.model.Expense;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseDataAccessServiceTest {

    // Start experiment
    @ClassRule
    public static GenericContainer<?> postgres =
        new GenericContainer<>("postgres:alpine")
        .withEnv("POSTGRES_USER", "postgres")
        .withEnv("POSTGRES_PASSWORD", "password")
        .withEnv("POSTGRES_DB", "docker_postgres")
        .withExposedPorts(5432)
        //.withNetwork(Network.newNetwork())
        //.withNetworkAliases("gateway")
        .waitingFor(new HostPortWaitStrategy());

    String address = postgres.getContainerIpAddress();
    Integer port = postgres.getFirstMappedPort();

    public static final String DRIVER = "org.postgresql.Driver";
    public final String JDBC_URL = "jdbc:postgresql://docker_postgres:"+ Integer.toString(port) +"/docker_postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "password";

    DataSource source = getDataSource(JDBC_URL);
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(source);

    // End experiment

    private ExpenseDataAccessService underTest;

    @Before
    public void setUp() {
        underTest = new ExpenseDataAccessService(jdbcTemplate);
    }

    @Test
    public void canPerformCrud() {

        // Given Expense: name "Rewe"
        UUID idOne = UUID.randomUUID();
        Expense expenseOne = new Expense(idOne, "Rewe");

        // Given Expense: name "Lidl"
        UUID idTwo = UUID.randomUUID();
        Expense expenseTwo = new Expense(idTwo, "Lidl");

        // Insert into db
        underTest.insertExpense(idOne, expenseOne);
        underTest.insertExpense(idTwo, expenseTwo);

        // Select "Rewe" from db
        assertThat(underTest.selectExpenseById(idOne))
                .isPresent()
                .hasValueSatisfying(expenseFromDb -> assertThat(expenseFromDb).isEqualToComparingFieldByField(expenseOne));

        // Select "Lidl" from db
        assertThat(underTest.selectExpenseById(idTwo))
                .isPresent()
                .hasValueSatisfying(expenseFromDb -> assertThat(expenseFromDb).isEqualToComparingFieldByField(expenseTwo));

        // Select all Expenses from db
        List<Expense> expenses = underTest.selectAllExpenses();

        // Size of List should be 2 and both entries "Rewe" and "Lidl" should be included
        assertThat(expenses)
                .hasSize(2)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expenseOne, expenseTwo);

        // Updated expense with name "Aldi" instead of "Rewe"
        Expense expenseUpdate = new Expense(idOne, "Aldi");

        // Update expense in db
        assertThat(underTest.updateExpenseById(idOne, expenseUpdate)).isEqualTo(1);

        // Select updated Expense from db which should have the name "Aldi" instead of "Rewe"
        assertThat(underTest.selectExpenseById(idOne))
                .isPresent()
                .hasValueSatisfying(expenseFromDb -> assertThat(expenseFromDb).isEqualToComparingFieldByField(expenseUpdate));

        // Delete updated Expense with name "Aldi" from db
        assertThat(underTest.deleteExpenseById(idOne)).isEqualTo(1);

        // Get expenseOne by Id, which should not exist
        assertThat(underTest.selectExpenseById(idOne)).isEmpty();

        // Select all expenses from db, only expense with name "Lidl" should exist
        assertThat(underTest.selectAllExpenses())
                .hasSize(1)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expenseTwo);
    }

    @Test
    public void willReturn0IfNoExpenseFoundToDelete() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        int deleteResult = underTest.deleteExpenseById(id);

        // Then
        assertThat(deleteResult).isEqualTo(0);
    }

    @Test
    public void willReturn0IfNoExpenseFoundToUpdate() {
        // Given
        UUID id = UUID.randomUUID();
        Expense expense = new Expense(id,"Hornbach not in Db");

        // When
        int updateResult = underTest.updateExpenseById(id, expense);

        // Then
        assertThat(updateResult).isEqualTo(0);
    }

    private DataSource getDataSource(String JDBC_URL) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(ExpenseDataAccessServiceTest.DRIVER);
        dataSource.setUrl(this.JDBC_URL);
        dataSource.setUsername(ExpenseDataAccessServiceTest.USERNAME);
        dataSource.setPassword(ExpenseDataAccessServiceTest.PASSWORD);
        return dataSource;
    }

}

