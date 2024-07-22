package gift.domain.exception.conflict;

public class OptionAlreadyExistsInProductException extends ConflictException {

    public OptionAlreadyExistsInProductException() {
        super("The options already exists in product.");
    }
}
