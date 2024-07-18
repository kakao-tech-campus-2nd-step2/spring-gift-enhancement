package gift.exception;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Long id;

    public CustomException(ErrorCode errorCode, Long id) {
        super(errorCode.getFormattedMessage(id));
        this.errorCode = errorCode;
        this.id = id;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Long getId() {
        return id;
    }
}
