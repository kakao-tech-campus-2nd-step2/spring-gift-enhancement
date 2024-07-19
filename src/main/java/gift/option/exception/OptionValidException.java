package gift.option.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class OptionValidException extends BusinessException {
    public OptionValidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
