package gift.domain.exception;

public class OptionNotFoundException extends RuntimeException {

    public OptionNotFoundException() {
        super("The option was not found.");
    }
}
