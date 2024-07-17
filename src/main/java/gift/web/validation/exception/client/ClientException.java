package gift.web.validation.exception.client;

import gift.web.validation.exception.ErrorRespondable;

public abstract class ClientException extends RuntimeException implements ErrorRespondable {

    protected ClientException() {
        super();
    }

    protected ClientException(String message) {
        super(message);
    }

    protected ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ClientException(Throwable cause) {
        super(cause);
    }

}
