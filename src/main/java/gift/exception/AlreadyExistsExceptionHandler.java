package gift.exception;

import gift.exception.product.ProductAlreadyExistsException;
import gift.exception.user.MemberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlreadyExistsExceptionHandler {
    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(MemberAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
