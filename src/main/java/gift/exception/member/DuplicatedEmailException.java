package gift.exception.member;

import org.springframework.http.HttpStatus;

public class DuplicatedEmailException extends RuntimeException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Email already exists";

    public DuplicatedEmailException() {
        super(MESSAGE);
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
