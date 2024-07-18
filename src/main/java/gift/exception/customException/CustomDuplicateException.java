package gift.exception.customException;

import gift.exception.ErrorCode;
import org.springframework.validation.BindingResult;

public class CustomDuplicateException extends CustomArgumentNotValidException {

    public CustomDuplicateException(
        BindingResult bindingResult, ErrorCode errorCode) {
        super(bindingResult, errorCode);
    }
}
