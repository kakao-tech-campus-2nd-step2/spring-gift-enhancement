package gift.domain.exception;

import gift.domain.exception.conflict.CategoryAlreadyExistsException;
import gift.domain.exception.conflict.CategoryHasProductsException;
import gift.domain.exception.conflict.ConflictException;
import gift.domain.exception.conflict.MemberAlreadyExistsException;
import gift.domain.exception.conflict.OptionAlreadyExistsInProductException;
import gift.domain.exception.conflict.ProductAlreadyExistsException;
import gift.domain.exception.forbidden.ForbiddenException;
import gift.domain.exception.forbidden.MemberIncorrectLoginInfoException;
import gift.domain.exception.forbidden.MemberNotAdminException;
import gift.domain.exception.forbidden.TokenExpiredException;
import gift.domain.exception.forbidden.TokenStringInvalidException;
import gift.domain.exception.notFound.CategoryNotFoundException;
import gift.domain.exception.notFound.MemberNotFoundException;
import gift.domain.exception.notFound.NotFoundException;
import gift.domain.exception.notFound.ProductNotFoundException;
import gift.domain.exception.notFound.ProductNotIncludedInWishlistException;
import gift.domain.exception.unauthorized.TokenNotFoundException;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.exception.unauthorized.TokenUnexpectedErrorException;
import gift.domain.exception.unauthorized.UnauthorizedException;
import gift.global.apiResponse.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 서버에서 발생하는 예외를 종합적으로 처리하는 클래스
 */
@RestControllerAdvice(basePackages = {"gift.domain", "gift.global"})
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        assert error != null;
        return ErrorApiResponse.of(error.getField() + ": " + error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        ProductNotFoundException.class,
        MemberNotFoundException.class,
        ProductNotIncludedInWishlistException.class,
        CategoryNotFoundException.class,
        OptionNotFoundException.class
    })
    public ResponseEntity<ErrorApiResponse> handleNotFoundException(NotFoundException e) {
        return ErrorApiResponse.notFound(e);
    }

    @ExceptionHandler({
        ProductAlreadyExistsException.class,
        MemberAlreadyExistsException.class,
        CategoryAlreadyExistsException.class,
        CategoryHasProductsException.class,
        OptionAlreadyExistsInProductException.class
    })
    public ResponseEntity<ErrorApiResponse> handleConflictException(ConflictException e) {
        return ErrorApiResponse.conflict(e);
    }

    @ExceptionHandler({
        MemberIncorrectLoginInfoException.class,
        MemberNotAdminException.class,
        TokenExpiredException.class,
        TokenStringInvalidException.class
    })
    public ResponseEntity<ErrorApiResponse> handleForbiddenException(ForbiddenException e) {
        return ErrorApiResponse.forbidden(e);
    }

    @ExceptionHandler({
        TokenNotFoundException.class,
        TokenUnexpectedErrorException.class
    })
    public ResponseEntity<ErrorApiResponse> handleUnauthorizedException(UnauthorizedException e) {
        return ErrorApiResponse.unauthorized(e);
    }
}
