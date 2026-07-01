package repository;

import model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    Expense add(Expense expense);

    Optional<Expense> update(Long id, Expense expense);

    void delete(Long id);

    List<Expense> findAll();

    Expense findById(Long id);

}
