package cli;

import picocli.CommandLine;
import service.SummaryService;

import java.time.Month;

@CommandLine.Command(name = "summary", description = "Show expense summary")
public class SummaryCommand implements Runnable{

    @CommandLine.Option(names = "--month")
    private Integer month;

    @Override
    public void run() {

        try {

            SummaryService service = new SummaryService(Main.FILE_NAME);

            if (month != null) {

                if (month < 1 || month > 12) {

                    System.out.println("Error: Invalid month. Use a value between 1 and 12.");

                    return;

                }

                Double total = service.findByMonth(month);

                String monthName = Month.of(month).name();

                System.out.printf("Total expenses for %s: $%.2f%n", monthName, total);

            } else {

                Double total = service.findAll();

                System.out.printf("Total expenses: $%.2f%n", total);

            }

        } catch (RuntimeException e) {

            System.out.println("Error: " + e.getCause().getMessage());

        }

    }
}
