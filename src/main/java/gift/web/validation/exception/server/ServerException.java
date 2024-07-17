package gift.web.validation.exception.server;


import gift.web.validation.exception.ErrorRespondable;

public abstract class ServerException extends RuntimeException implements ErrorRespondable {

    protected ServerException() {
        super();
    }

    protected ServerException(String message) {
        super(message);
    }

    protected ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServerException(Throwable cause) {
        super(cause);
    }

}
