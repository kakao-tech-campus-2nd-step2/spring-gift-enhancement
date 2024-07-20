package gift.domain.exception.badRequest;

public class OptionQuantityOutOfRangeException extends BadRequestException {

    public OptionQuantityOutOfRangeException() {
        super("The option quantity must be greater than or equal to 1 and less than or equal to 100000000.");
    }
}
