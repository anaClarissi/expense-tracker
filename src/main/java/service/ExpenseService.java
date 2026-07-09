package service;

import exceptions.ExpenseNotFoundException;
import exceptions.InvalidAmountException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidDescriptionException;
import model.Category;
import model.Expense;
import repository.ExpenseRepository;
import repository.ExpenseRepositoryGson;

import java.time.LocalDate;
import java.util.List;

public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(String fileName) {

        this.repository = new ExpenseRepositoryGson(fileName);

    }

    public Expense add(String description, Double amount, Category category) {

        try {

            Long id = findAll().stream().map(Expense::getId).max(Long::compareTo).orElse(0L) + 1L;

            description = checkDescription(description);

            amount = checkAmount(amount);

            category = checkCategory(category);

            Expense expense = new Expense(id, description, amount, category, LocalDate.now());

            return repository.add(expense);

        } catch (InvalidAmountException e) {

            throw new InvalidAmountException();

        } catch (InvalidDescriptionException e) {

            throw new InvalidDescriptionException();

        } catch (RuntimeException e) {

            throw new RuntimeException("Error: ", e);

        }

    }

    public Expense update(Long id, Expense obj) {

        try {

            Expense expense = findById(id);

            String description = checkDescription(obj.getDescription());

            Double amount = checkAmount(obj.getAmount());

            Category category = obj.getCategory() == null ? expense.getCategory() : obj.getCategory();

            expense.setDescription(description);

            expense.setAmount(amount);

            expense.setCategory(category);

            expense.setDate(LocalDate.now());

            return repository.update(id, expense);

        } catch (InvalidAmountException e) {

            throw new InvalidAmountException();

        } catch (InvalidDescriptionException e) {

            throw new InvalidDescriptionException();

        } catch (RuntimeException e) {

            throw new RuntimeException("Error: ", e);

        }

    }

    public Expense delete(Long id) {

        try {

            Expense expense = findById(id);

            repository.delete(id);

            return expense;

        } catch (RuntimeException e) {

            throw new RuntimeException("Error: ", e);

        }

    }

    public Expense findById(Long id) {

        if (id == null) throw new RuntimeException("Id Inválid");

        try {

            return repository.findById(id, findAll());

        } catch (RuntimeException e) {

            throw new ExpenseNotFoundException(id);

        }

    }

    public List<Expense> findAll() {

        return repository.findAll();

    }

    private String checkDescription(String description) {

        if (description == null) throw new InvalidDescriptionException();

        if (description.isBlank()) throw new InvalidDescriptionException();

        return description;

    }

    private Double checkAmount(Double amount) {

        if (amount == null) throw new RuntimeException("Invalid amount");

        if (amount <= 0) throw new InvalidAmountException();

        return amount;

    }

    private Category checkCategory(Category category) {

        if (category == null) {

            category = Category.OTHER;

        }

        return category;

    }

}
