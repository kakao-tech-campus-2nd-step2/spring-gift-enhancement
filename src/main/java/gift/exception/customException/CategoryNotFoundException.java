package gift.exception.customException;

import gift.exception.ErrorCode;

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
