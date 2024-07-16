package gift.global.exception;

public class DBException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String details;

    public DBException(ErrorCode errorCode, String details) {
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
