package exceptions;

public class InvalidMonthException extends RuntimeException {

    public InvalidMonthException() {

        super("Invalid month. User a value between 1 and 12.");

    }

}
