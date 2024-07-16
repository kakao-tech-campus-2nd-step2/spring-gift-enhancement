package gift.exception;

public class DuplicateMemberEmailException extends RuntimeException {
    public DuplicateMemberEmailException(String message) {
        super(message);
    }
}
