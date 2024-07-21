package gift.exception.categortException;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super("Category with id " + categoryId + " not found");
    }
}

