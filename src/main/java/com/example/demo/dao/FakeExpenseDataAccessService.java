/*
        package com.example.demo.dao;


        import com.example.demo.model.Expense;
        import org.springframework.stereotype.Repository;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;
        import java.util.UUID;

        @Repository("fakeDao")
public class FakeExpenseDataAccessService implements ExpenseDao {

    private static List<Expense> DB = new ArrayList<>();

    @Override
    public int insertExpense(UUID id, Expense expense) {
        DB.add(new Expense(id,expense.getName(), expense.getAmount(), expense.getUserid(), expense.getDate()));
        return 1;
    }

    @Override
    public List<Expense> selectAllExpenses() {
        return DB;
    }

    @Override
    public Expense selectExpenseById(UUID id) {
        return DB.stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteExpenseById(UUID id) {
        Optional<Expense> expenseMaybe = selectExpenseById(id);
        if(expenseMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(expenseMaybe.get());
        return 1;
    }




    @Override
    public int updateExpenseById(UUID id, Expense updatedExpense) {
        return selectExpenseById(id)
                .map(expense -> {
                    int indexOfExpenseToUpdate = DB.indexOf(expense);
                    if (indexOfExpenseToUpdate >= 0) {
                        DB.set(indexOfExpenseToUpdate, new Expense(id, updatedExpense.getName(), updatedExpense.getAmount(), updatedExpense.getUserid(), updatedExpense.getDate()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}

 */

    /*
     * Now what does it do: first of all, the ".orElse" at the end is a method of the "Optional" return value
     * of "selectExpenseById". It is returned when an Optional value is not present.
     * Note: there is also an ".orElseGet", which takes a function that needs to be evaluated. This function will
     * only be evaluated, if an Optional is not present. Whereas when we pass a function to .orElse,
     * the function will always be evaluated, even if there is an Optional, because per default, .orElse
     * holds a simple variable ready. So only use orElse with a static value and for every evaluated/computed value,
     * use .orElseGet
     *
     * And for the rest: selectExpenseById returns the "old" expense present in our database based on the id that
     * we provided. The .map takes the input object/stream (in this case: only 1 object of type "Expense") and converts
     * it to another type of object (in this case into an Integer -> 0 or 1).
     * And in between, we are applying some function which assigns "indexOfExpenseToUpdate" the index of the
     * Expense that is the result of selectExpenseById(id), then basically checks if that index - and therefore the
     * entry - exist in the database, and set the Expense with this index to have a new name which is
     * "updatedExpense.getName()"
     *
     * If no index >= 0 is found, or there is no "old" Expense in the first place as a result of ExpenseById, then we
     * return 0.
     * -> this can later be converted to throw a 404 Error etc...
     * */



