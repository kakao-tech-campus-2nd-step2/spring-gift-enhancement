package gift.domain.exception.badRequest;

import gift.domain.exception.ServerException;

public abstract class BadRequestException extends ServerException {

    public BadRequestException(String message) {
        super(message);
    }
}
