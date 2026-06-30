package repository;

import model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    Expense add(Expense expense);

    Optional<Expense> update(Long id, Expense expense);

    Optional<Expense> delete(Long id);

    List<Expense> findAll();

    Optional<Expense> findById(Long id);

}
