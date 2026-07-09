package repository;

import model.Category;
import model.Expense;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseRepositoryTest {

    private ExpenseRepository repository;

    private static final String TEST_FILE = "test_repository.json";


    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_DESCRIPTION = "Lunch";

    private static final Double DEFAULT_AMOUNT = 20.0;

    private static final Category DEFAULT_CATEGORY = Category.FOOD;


    @BeforeEach
    void setUp() {

        repository = new ExpenseRepositoryGson(TEST_FILE);

    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(Path.of(TEST_FILE));

    }


    @Test
    void shouldCreateFileIfNotExists() {

        assertTrue(Files.exists(Path.of(TEST_FILE)));

    }

    @Test
    void shouldReturnEmptyListWhenFileIsEmpty() {

        List<Expense> list = repository.findAll();

        assertTrue(list.isEmpty());

    }

    @Test
    void shouldSaveAndReadExpensesFromFile() {

        Expense expense = new Expense(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY, LocalDate.now());

        repository.add(expense);

        List<Expense> list = repository.findAll();

        assertEquals(1, list.size());
        assertEquals(DEFAULT_DESCRIPTION, list.get(0).getDescription());
        assertEquals(DEFAULT_AMOUNT, list.get(0).getAmount());

    }

    @Test
    void shouldDeleteExpenseFromFile() {

        Expense expense = new Expense(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY, LocalDate.now());

        repository.add(expense);
        repository.delete(DEFAULT_ID);

        assertTrue(repository.findAll().isEmpty());

    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {

        assertThrows(RuntimeException.class, () -> repository.findById(999L, repository.findAll()));

    }

}
