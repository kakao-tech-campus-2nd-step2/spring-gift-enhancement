package gift.domain.exception;

public class CategoryHasProductsException extends RuntimeException {

    public CategoryHasProductsException() {
        super("This category cannot be deleted because some products are included in it.");
    }
}
