package gift.exception.customException;

import gift.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;

public class DuplicateCategoryException extends CustomArgumentNotValidException {

    public DuplicateCategoryException(MethodParameter methodParameter,
        BindingResult bindingResult, ErrorCode errorCode) {
        super(methodParameter, bindingResult, errorCode);
    }
}
