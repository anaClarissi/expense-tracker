package cli;

import model.Category;
import model.Expense;
import picocli.CommandLine;
import service.ExpenseService;
import utils.CategoryConverter;

import java.util.List;

@CommandLine.Command(name = "list", description = "List all expenses")
public class ListCommand implements Runnable {

    @CommandLine.Option(names = "--category", converter = CategoryConverter.class)
    private Category category;

    @Override
    public void run() {

        try {

            ExpenseService service = new ExpenseService(Main.FILE_NAME);

            List<Expense> list = service.findAll();

            if (category != null) {

                list = list.stream().filter(expense -> expense.getCategory() == category).toList();

            }

            if (list.isEmpty()) {

                System.out.println("No expenses found.");

                return;

            }

            System.out.printf("%-5s %-12s %-20s %-10s %-15s%n",
                    "ID", "Date", "Description", "Amount", "Category");

            list.forEach(expense -> {

                System.out.printf("%-5d %-12s %-20s $%-9.2f %-15s%n",
                        expense.getId(), expense.getDate(), expense.getDescription(),
                        expense.getAmount(), expense.getCategory());

            });

        } catch (RuntimeException e) {

            System.out.println("Error: " + e.getCause().getMessage());

        }

    }
}
