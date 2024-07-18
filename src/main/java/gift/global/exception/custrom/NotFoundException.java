package gift.global.exception.custrom;


import gift.global.exception.ErrorCode;

public class NotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String details;

    public NotFoundException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}
