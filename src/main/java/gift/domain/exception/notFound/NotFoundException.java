package gift.domain.exception.notFound;

import gift.domain.exception.ServerException;

public abstract class NotFoundException extends ServerException {

    public NotFoundException(String message) {
        super(message);
    }
}
