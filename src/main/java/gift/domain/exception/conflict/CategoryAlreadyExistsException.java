package gift.domain.exception.conflict;

public class CategoryAlreadyExistsException extends ConflictException {

    public CategoryAlreadyExistsException() {
        super("This category name already exists. Try other one.");
    }
}
