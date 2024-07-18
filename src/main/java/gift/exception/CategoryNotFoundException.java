package gift.exception;

import gift.constant.ErrorMessage;

public class CategoryNotFoundException extends GiftException {

    public CategoryNotFoundException() {
        super(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
