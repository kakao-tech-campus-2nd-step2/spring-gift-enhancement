package gift.exception.customException;

import gift.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
