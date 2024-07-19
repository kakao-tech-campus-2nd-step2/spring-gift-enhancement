package gift.domain.exception.unauthorized;

import gift.domain.exception.ServerException;

public abstract class UnauthorizedException extends ServerException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
