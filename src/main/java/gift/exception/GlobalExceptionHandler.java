package gift.exception;

import gift.exception.member.DuplicatedEmailException;
import gift.exception.member.InvalidAccountException;
import gift.exception.member.PasswordMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> HandleDuplicatedEmail(DuplicatedEmailException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> HandlePasswordMismatch(PasswordMismatchException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<String> HandleInvalidAccount(InvalidAccountException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
