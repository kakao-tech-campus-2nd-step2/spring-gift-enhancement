package gift.exception;

import gift.constant.ErrorMessage;

public class CategoryNameNotDuplicateException extends GiftException {

    public CategoryNameNotDuplicateException() {
        super(ErrorMessage.CATEGORY_NAME_NOT_DUPLICATES.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 409;
    }

}
