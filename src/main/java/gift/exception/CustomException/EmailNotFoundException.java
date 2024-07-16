package gift.exception.CustomException;

import gift.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;

public class EmailNotFoundException extends CustomArgumentNotValidException {

    public EmailNotFoundException(MethodParameter parameter,
        BindingResult bindingResult, ErrorCode errorCode) {
        super(parameter, bindingResult, errorCode);
    }
}
