package gift.common.exception;

import gift.option.OptionErrorCode;
import org.springframework.http.HttpStatus;

public class OptionException extends RuntimeException {

    private final OptionErrorCode optionErrorCode;
    private final HttpStatus httpStatus;
    private final String detailedMessage;

    public OptionException(OptionErrorCode optionErrorCode) {
        super(optionErrorCode.getMessage());
        this.optionErrorCode = optionErrorCode;
        this.httpStatus = optionErrorCode.getHttpStatus();
        this.detailedMessage = optionErrorCode.getMessage();
    }
}
