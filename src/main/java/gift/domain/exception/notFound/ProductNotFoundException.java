package gift.domain.exception.notFound;

/**
 * 상품을 찾을 수 없을 때 발생하는 예외
 */
public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException() {
        super("The product was not found.");
    }
}
