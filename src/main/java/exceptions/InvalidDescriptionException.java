package exceptions;

public class InvalidDescriptionException extends RuntimeException {

    public InvalidDescriptionException() {

        super("Description cannot be empty.");

    }

}
