package gift.exception.customException;

import gift.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;

public class DuplicateEmailException extends CustomArgumentNotValidException {

    public DuplicateEmailException(MethodParameter parameter,
        BindingResult bindingResult, ErrorCode errorCode) {
        super(parameter, bindingResult, errorCode);
    }
}
