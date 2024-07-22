package gift.domain.exception.conflict;

/**
 * 상품이 이미 존재할 때 발생하는 예외
 */
public class ProductAlreadyExistsException extends ConflictException {

    public ProductAlreadyExistsException() {
        super("The product already exists.");
    }
}
