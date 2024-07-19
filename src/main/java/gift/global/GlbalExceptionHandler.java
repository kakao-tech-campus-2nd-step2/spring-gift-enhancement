package gift.global;

import gift.domain.member.exception.MemberAuthorizationException;
import gift.domain.member.exception.MemberNotFoundException;
import gift.domain.option.exception.DuplicateOptionNameException;
import gift.domain.option.exception.OptionNotFoundException;
import gift.domain.product.exception.ProductNotFoundException;
import gift.domain.wishlist.exception.WishNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlbalExceptionHandler {

    @ExceptionHandler(value = MemberAuthorizationException.class)
    public ResponseEntity<String> handleMemberException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(WishNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(WishNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(OptionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleNameException(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorMessages);
    }
    @ExceptionHandler(DuplicateOptionNameException.class)
    public ResponseEntity<?> handleEntityNotFoundException(DuplicateOptionNameException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }
}
