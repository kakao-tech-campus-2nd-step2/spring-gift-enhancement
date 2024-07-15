package gift.common.exception;

import gift.category.dto.CategoryReqDto;
import gift.category.exception.CategoryErrorCode;
import gift.product.dto.ProductReqDto;
import gift.product.exception.ProductErrorCode;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Map<Class<?>, ErrorCode> errorCodeMap = Map.of(
            ProductReqDto.class, ProductErrorCode.INVALID_INPUT_VALUE_PRODUCT,
            CategoryReqDto.class, CategoryErrorCode.INVALID_INPUT_VALUE_CATEGORY
    );

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(  // 파라미터 유효성 검사 실패 시 발생하는 예외 처리
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;  // 기본적으로 INVALID_INPUT_VALUE로 처리

        Class<?> parameterType = ex.getParameter().getParameterType();

        if (errorCodeMap.containsKey(parameterType)) {
            errorCode = getErrorCode(parameterType);
        }

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(ex, errorCode));
    }

    private ErrorCode getErrorCode(Class<?> parameterType) {
        return errorCodeMap.getOrDefault(parameterType, CommonErrorCode.INVALID_INPUT_VALUE);
    }

    private ErrorResponse makeErrorResponse(MethodArgumentNotValidException ex, ErrorCode errorCode) {

        ErrorResponse problemDetail = new ErrorResponse(errorCode);

        List<ValidationError> invalidParams = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .toList();

        problemDetail.setInvalidParams(invalidParams);

        return problemDetail;
    }
}
