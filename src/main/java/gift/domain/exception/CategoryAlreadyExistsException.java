package gift.domain.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException() {
        super("This category name already exists. Try other one.");
    }
}
