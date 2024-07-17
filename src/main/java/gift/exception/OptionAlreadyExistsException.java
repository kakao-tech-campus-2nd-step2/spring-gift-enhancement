package gift.exception;

import gift.constant.ErrorMessage;

public class OptionAlreadyExistsException extends GiftException {

    public OptionAlreadyExistsException() {
        super(ErrorMessage.OPTION_ALREADY_EXISTS.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 409;
    }

}
