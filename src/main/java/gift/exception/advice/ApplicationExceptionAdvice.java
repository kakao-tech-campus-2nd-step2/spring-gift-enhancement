package gift.exception.advice;

import gift.exception.ResourceNotFoundException;
import gift.util.ResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionAdvice {

    private final ResponseUtility responseUtility;

    public ApplicationExceptionAdvice(ResponseUtility responseUtility) {
        this.responseUtility = responseUtility;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseUtility.makeResponse(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseUtility.makeResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleForbidden(ResponseStatusException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(responseUtility.makeResponse(e.getMessage()));
    }
}
