package cli;

import picocli.CommandLine;
import service.ExpenseService;

@CommandLine.Command(name = "delete", description = "Delete an expense")
public class DeleteCommand implements Runnable{

    @CommandLine.Option(names = "--id", required = true)
    private Long id;

    @Override
    public void run() {

        try {

            ExpenseService service = new ExpenseService(Main.FILE_NAME);

            service.delete(id);

            System.out.println("Expense deleted successfully (ID: " + id + ")");

        } catch (RuntimeException e) {

            System.out.println("Error: " + e.getCause().getMessage());

        }

    }
}
