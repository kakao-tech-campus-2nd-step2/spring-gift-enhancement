package gift.exception.CustomException;

import gift.exception.ErrorCode;

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
