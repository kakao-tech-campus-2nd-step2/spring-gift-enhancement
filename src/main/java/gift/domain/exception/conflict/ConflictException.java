package gift.domain.exception.conflict;

import gift.domain.exception.ServerException;

public abstract class ConflictException extends ServerException {

    public ConflictException(String message) {
        super(message);
    }
}
