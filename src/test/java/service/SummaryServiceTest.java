package service;

import model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryServiceTest {

    private SummaryService summaryService;

    private ExpenseService expenseService;

    private static final String TEST_FILE = "test_summary.json";


    private static final String PRIMARY_DEFAULT_DESCRIPTION = "Lunch";

    private static final Double PRIMARY_DEFAULT_AMOUNT = 20.0;

    private static final Category PRIMARY_DEFAULT_CATEGORY = Category.FOOD;


    private static final String SECONDARY_DEFAULT_DESCRIPTION = "Bus";

    private static final Double SECONDARY_DEFAULT_AMOUNT = 10.0;

    private static final Category SECONDARY_DEFAULT_CATEGORY = Category.TRANSPORT;


    @BeforeEach
    void setUp() {

        summaryService = new SummaryService(TEST_FILE);

        expenseService = new ExpenseService(TEST_FILE);

    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(Path.of(TEST_FILE));

    }


    @Test
    void shouldReturnTotalOfAllExpenses() {

        expenseService.add(PRIMARY_DEFAULT_DESCRIPTION, PRIMARY_DEFAULT_AMOUNT, PRIMARY_DEFAULT_CATEGORY);

        expenseService.add(SECONDARY_DEFAULT_DESCRIPTION, SECONDARY_DEFAULT_AMOUNT, SECONDARY_DEFAULT_CATEGORY);

        Double sum = PRIMARY_DEFAULT_AMOUNT + SECONDARY_DEFAULT_AMOUNT;

        Double total = summaryService.findAll();

        assertEquals(sum, total);

    }

    @Test
    void shouldReturnZeroWhenNoExpense() {

        Double total = summaryService.findAll();

        assertEquals(0.0, total);

    }

    @Test
    void shouldReturnTotalForSpecificMonth() {

        expenseService.add(PRIMARY_DEFAULT_DESCRIPTION, PRIMARY_DEFAULT_AMOUNT, PRIMARY_DEFAULT_CATEGORY);

        expenseService.add(SECONDARY_DEFAULT_DESCRIPTION, SECONDARY_DEFAULT_AMOUNT, SECONDARY_DEFAULT_CATEGORY);

        Double sum = PRIMARY_DEFAULT_AMOUNT + SECONDARY_DEFAULT_AMOUNT;

        int currentMonth = java.time.LocalDate.now().getMonthValue();

        Double total = summaryService.findByMonth(currentMonth);

        assertEquals(sum, total);

    }

    @Test
    void shouldReturnZeroWhenNoExpenseInMonth() {

        expenseService.add(PRIMARY_DEFAULT_DESCRIPTION, PRIMARY_DEFAULT_AMOUNT, PRIMARY_DEFAULT_CATEGORY);

        Double total = summaryService.findByMonth(1);

        assertEquals(0.0, total);

    }

}
