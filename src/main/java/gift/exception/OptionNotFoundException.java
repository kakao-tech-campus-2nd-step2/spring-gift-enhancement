package gift.exception;

import gift.constant.ErrorMessage;

public class OptionNotFoundException extends GiftException {

    public OptionNotFoundException() {
        super(ErrorMessage.OPTION_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
