package cli;

import picocli.CommandLine;

import java.util.Locale;

@CommandLine.Command(
        name = "expense-tracker",
        subcommands = {
                AddCommand.class,
                UpdateCommand.class,
                DeleteCommand.class,
                ListCommand.class,
                SummaryCommand.class
        },
        mixinStandardHelpOptions = true
)
public class Main implements Runnable {

    public static final String FILE_NAME = "expenses.json";

    @Override
    public void run() {

        CommandLine.usage(this, System.out);

    }

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        int code = new CommandLine(new Main())
                .setParameterExceptionHandler((exception, arguments) -> {

                    String message = exception.getCause() != null
                            ? exception.getCause().getMessage()
                            : exception.getMessage();

                    System.out.println("Error: " + message);

                    return 1;

                }).execute(args);

        System.exit(code);

    }

}
