package gift.domain.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("The category was not found.");
    }
}
