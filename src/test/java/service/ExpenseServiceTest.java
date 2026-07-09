package service;

import exceptions.ExpenseNotFoundException;
import exceptions.InvalidAmountException;
import exceptions.InvalidDescriptionException;
import model.Category;
import model.Expense;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseServiceTest {

    private ExpenseService service;

    private static final String TEST_FILE = "test_expense.json";


    private static final String DEFAULT_DESCRIPTION = "Lunch";

    private static final Double DEFAULT_AMOUNT = 20.0;

    private static final Category DEFAULT_CATEGORY = Category.FOOD;

    @BeforeEach
    void setUp() {

        service = new ExpenseService(TEST_FILE);

    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(Path.of(TEST_FILE));

    }


    @Test
    void shouldAddExpenseSuccessfully() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        assertNotNull(expense);
        assertEquals(DEFAULT_DESCRIPTION, expense.getDescription());
        assertEquals(DEFAULT_AMOUNT, expense.getAmount());
        assertEquals(DEFAULT_CATEGORY, expense.getCategory());
        assertNotNull(expense.getDate());

    }

    @Test
    void shouldUpdateExpenseSuccessfully() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        Expense updated = service.update(1L, new Expense(null, "Bike", 1200.0, Category.TRANSPORT, null));

        assertEquals("Bike", updated.getDescription());
        assertEquals(1200.0, updated.getAmount());

    }

    @Test
    void shouldDeleteExpenseSuccessfully() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        service.delete(1L);

        assertTrue(service.findAll().isEmpty());

    }

    @Test
    void shouldFindExpenseSuccessfully() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        Expense found = service.findById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());

    }

    @Test
    void shouldReturnAllExpenses() {

        service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);
        service.add("Bus", 10.0, Category.TRANSPORT);

        assertEquals(2, service.findAll().size());

    }


    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {

        assertThrows(InvalidAmountException.class, () -> service.add(DEFAULT_DESCRIPTION, -10.0, DEFAULT_CATEGORY));

    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        assertThrows(InvalidAmountException.class, () -> service.add(DEFAULT_DESCRIPTION, 0.0, DEFAULT_CATEGORY));

    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsBlank() {

        assertThrows(InvalidDescriptionException.class, () -> service.add("   ", DEFAULT_AMOUNT, DEFAULT_CATEGORY));

    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {

        assertThrows(InvalidDescriptionException.class, () -> service.add(null, DEFAULT_AMOUNT, DEFAULT_CATEGORY));

    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {

        assertThrows(ExpenseNotFoundException.class, () -> service.findById(999L));

    }


    @Test
    void shouldSetCategoryToOtherWhenNull() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, null);

        assertEquals(Category.OTHER, expense.getCategory());

    }

    @Test
    void shouldGenerateIncrementalIds() {

        Expense first = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        Expense second = service.add("TV", 2000.0, Category.ENTERTAINMENT);


        assertEquals(1L, first.getId());

        assertEquals(2L, second.getId());

    }

    @Test
    void shouldSetDateToTodayWhenAdding() {

        Expense expense = service.add(DEFAULT_DESCRIPTION, DEFAULT_AMOUNT, DEFAULT_CATEGORY);

        assertEquals(java.time.LocalDate.now(), expense.getDate());

    }

}
