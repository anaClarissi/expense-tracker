package repository;

import model.Expense;

import java.util.List;

public interface ExpenseRepository {

    Expense add(Expense expense);

    Expense update(Long id, Expense expense);

    void delete(Long id);

    List<Expense> findAll();

    Expense findById(Long id, List<Expense> list);

}
