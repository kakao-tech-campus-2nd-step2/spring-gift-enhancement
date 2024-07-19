package gift.option.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL_ERROR);
    }
}
