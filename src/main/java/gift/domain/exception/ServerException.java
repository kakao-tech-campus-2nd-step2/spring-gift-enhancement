package gift.domain.exception;

public abstract class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }
}
