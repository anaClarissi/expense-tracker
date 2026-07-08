package cli;

import model.Category;
import model.Expense;
import picocli.CommandLine;
import service.ExpenseService;
import utils.CategoryConverter;

@CommandLine.Command(name = "update", description = "Update an existing expense")
public class UpdateCommand implements Runnable{

    @CommandLine.Option(names = "--id", required = true)
    private Long id;

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

            Expense expense = new Expense(null, description, amount, category, null);

            service.update(id, expense);

            System.out.println("Expense updated successfully (ID: " + id + ")");

        } catch (RuntimeException e) {

            System.out.println("Error: " + e.getCause().getMessage());

        }

    }

}
