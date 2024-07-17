package gift.exception.customException;

import gift.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;

public class PassWordMissMatchException extends CustomArgumentNotValidException {

    public PassWordMissMatchException(MethodParameter parameter,
        BindingResult bindingResult, ErrorCode errorCode) {
        super(parameter, bindingResult, errorCode);
    }
}
