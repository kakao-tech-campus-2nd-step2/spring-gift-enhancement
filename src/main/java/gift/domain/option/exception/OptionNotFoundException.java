package gift.domain.option.exception;

public class OptionNotFoundException extends RuntimeException{
    public OptionNotFoundException(String message) {
        super(message);
    }
}
