package gift.domain.exception.notFound;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException() {
        super("The category was not found.");
    }
}
