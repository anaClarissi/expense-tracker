package cli;

import model.Category;
import model.Expense;
import picocli.CommandLine;
import service.ExpenseService;
import utils.CategoryConverter;

@CommandLine.Command(name = "add", description = "Add a new expense")
public class AddCommand implements Runnable{

    @CommandLine.Option(names = "--description", required = true)
    private String description;

    @CommandLine.Option(names = "--amount", required = true)
    private Double amount;

    @CommandLine.Option(names = "--category", converter = CategoryConverter.class)
    private Category category;

    @Override
    public void run() {

        try {

            ExpenseService service = new ExpenseService(Main.FILE_NAME);

            Expense expense = service.add(description, amount, category);

            System.out.println("Expense added successfully (ID: " + expense.getId() + ")");

        } catch (RuntimeException e) {

            System.out.println("Error: " + e.getCause().getMessage());

        }

    }
}
