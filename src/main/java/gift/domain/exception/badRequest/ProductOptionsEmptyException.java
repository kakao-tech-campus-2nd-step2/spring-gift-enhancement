package gift.domain.exception.badRequest;

public class ProductOptionsEmptyException extends BadRequestException {

    public ProductOptionsEmptyException() {
        super("A product must have at least one option.");
    }
}
