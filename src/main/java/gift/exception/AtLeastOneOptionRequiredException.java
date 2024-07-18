package gift.exception;

import gift.constant.ErrorMessage;

public class AtLeastOneOptionRequiredException extends GiftException {

    public AtLeastOneOptionRequiredException() {
        super(ErrorMessage.NO_OPTIONS_IN_PRODUCT.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
