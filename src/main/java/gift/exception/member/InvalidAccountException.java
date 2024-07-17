package gift.exception.member;

import org.springframework.http.HttpStatus;

public class InvalidAccountException extends RuntimeException {
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "Invalid Email or Password";

    public InvalidAccountException() {
        super(MESSAGE);
    }

    public static HttpStatus getStatus() {
        return STATUS;
    }
}
