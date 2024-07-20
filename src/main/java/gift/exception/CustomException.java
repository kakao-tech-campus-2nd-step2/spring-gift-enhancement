package gift.exception;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object id;

    public CustomException(ErrorCode errorCode, Object id) {
        super(errorCode.getFormattedMessage(id));
        this.errorCode = errorCode;
        this.id = id;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object getId() {
        return id;
    }
}
