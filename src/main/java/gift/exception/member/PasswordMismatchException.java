package gift.exception.member;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends RuntimeException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Password and password confirmation do not match";

    public PasswordMismatchException() {
        super(MESSAGE);
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
