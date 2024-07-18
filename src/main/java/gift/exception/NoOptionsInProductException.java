package gift.exception;

import gift.constant.ErrorMessage;

public class NoOptionsInProductException extends GiftException {

    public NoOptionsInProductException() {
        super(ErrorMessage.AT_LEAST_ONE_OPTION_REQUIRED.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
