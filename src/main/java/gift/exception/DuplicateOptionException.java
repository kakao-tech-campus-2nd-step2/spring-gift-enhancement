package gift.exception;

import gift.constant.ErrorMessage;

public class DuplicateOptionException extends GiftException {

    public DuplicateOptionException() {
        super(ErrorMessage.DUPLICATE_OPTION.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 409;
    }

}
