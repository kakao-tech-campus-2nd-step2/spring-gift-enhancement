package gift.domain.exception.conflict;

public class CategoryHasProductsException extends ConflictException {

    public CategoryHasProductsException() {
        super("This category cannot be deleted because some products are included in it.");
    }
}
