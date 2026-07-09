package exceptions;

public class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException(String value) {

        super("Invalid category: '" + value + "'. Valid values: FOOD, TRANSPORT, HEALTH, ENTERTAINMENT, EDUCATION, OTHER.");

    }

}
