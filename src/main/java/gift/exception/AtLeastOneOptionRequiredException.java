package gift.exception;

import gift.constant.ErrorMessage;

public class AtLeastOneOptionRequiredException extends GiftException {

    public AtLeastOneOptionRequiredException() {
        super(ErrorMessage.AT_LEAST_ONE_OPTION_REQUIRED.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
