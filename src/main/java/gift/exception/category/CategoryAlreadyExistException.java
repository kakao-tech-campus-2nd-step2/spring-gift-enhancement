package gift.exception.category;

public class CategoryAlreadyExistException extends RuntimeException {

    public CategoryAlreadyExistException() {
        this("이미 존재하는 카테고리입니다.");
    }

    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
