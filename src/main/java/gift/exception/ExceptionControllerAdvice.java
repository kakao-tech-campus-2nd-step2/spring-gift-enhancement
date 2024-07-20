package gift.exception;

import gift.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalid(MethodArgumentNotValidException e) {
        ErrorCode error = ErrorCode.VALIDATION_ERROR;

        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .code(error.getStatus().value())
                .message(error.getMessage())
                .build();

        e.getFieldErrors().forEach(fieldError -> response.addValidation(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(GiftException.class)
    public ResponseEntity<ErrorResponse> giftException(GiftException e) {
        HttpStatus status = e.getErrorMessage().getStatus();

        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .code(status.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(status)
                .body(response);
    }

}
