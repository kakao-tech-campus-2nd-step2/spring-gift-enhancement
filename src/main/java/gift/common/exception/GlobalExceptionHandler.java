package gift.common.exception;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", ex.getStatusCode().toString());
        error.put("message", ex.getReason());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());;
        problemDetail.setType(URI.create("/errors/category-not-found"));
        URI.create("/errors/category-not-found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());;
        problemDetail.setType(URI.create("/errors/product-not-found"));
        URI.create("/errors/product-not-found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(WishListNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleWishListNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());;
        problemDetail.setType(URI.create("/errors/wish-not-found"));
        URI.create("/errors/wish-not-found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleOptionNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());;
        problemDetail.setType(URI.create("/errors/option-not-found"));
        URI.create("/errors/option-not-found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(AlreadyExistName.class)
    public ResponseEntity<ProblemDetail> handleAlreadyExistName(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());;
        problemDetail.setType(URI.create("/errors/option-not-found"));
        URI.create("/errors/option-not-found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}




