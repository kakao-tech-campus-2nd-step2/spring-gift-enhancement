package gift.exception.category;

import gift.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException() {
        this("해당 카테고리를 찾을 수 없습니다.");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
