package gift.domain.exception.conflict;

public class MemberAlreadyExistsException extends ConflictException {

    public MemberAlreadyExistsException() {
        super("Your email already registered. Retry with other one.");
    }
}
