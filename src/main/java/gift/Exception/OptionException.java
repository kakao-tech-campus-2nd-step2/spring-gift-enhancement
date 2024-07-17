package gift.Exception;

public class OptionException extends RuntimeException {

    public OptionException() {
        super();
    }

    public OptionException(String message) {
        super(message);
    }

    public OptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptionException(Throwable cause) {
        super(cause);
    }

    protected OptionException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
