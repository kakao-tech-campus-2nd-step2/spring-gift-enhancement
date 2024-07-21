package gift.exception;

import gift.dto.ResponseDTO;
import gift.exception.BadRequestExceptions.UserNotFoundException;
import gift.exception.InternalServerExceptions.InternalServerException;
import gift.util.ResponseEntityUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseEntityUtil.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("\n");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("\n");
        }
        return new ResponseEntity<>(new ResponseDTO(true, errorMessage.toString().trim()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("\n");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage()).append("\n");
        }
        return new ResponseEntity<>(new ResponseDTO(true, errorMessage.toString().trim()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(
            HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ResponseDTO(true, "가격은 숫자여야 합니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(
            MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ResponseDTO(true, "상품의 개수는 숫자로 입력해야 합니다."),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(UserNotFoundException ex) {
        return new ResponseEntity<>(new ResponseDTO(true, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(InternalServerException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseDTO(true, "예상치 못한 에러입니다. 관리자에게 연락 주시기 바랍니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
