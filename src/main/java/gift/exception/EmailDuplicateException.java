package gift.exception;

import gift.constant.ErrorMessage;

public class EmailDuplicateException extends GiftException {

    public EmailDuplicateException() {
        super(ErrorMessage.DUPLICATED_EMAIL.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 409;
    }

}
