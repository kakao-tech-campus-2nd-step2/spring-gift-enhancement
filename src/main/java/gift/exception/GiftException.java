package gift.exception;

public class GiftException extends RuntimeException {

    private ErrorMessage errorMessage;

    public GiftException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
