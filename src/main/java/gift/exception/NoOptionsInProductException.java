package gift.exception;

import gift.constant.ErrorMessage;

public class NoOptionsInProductException extends GiftException {

    public NoOptionsInProductException() {
        super(ErrorMessage.NO_OPTIONS_IN_PRODUCT.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
