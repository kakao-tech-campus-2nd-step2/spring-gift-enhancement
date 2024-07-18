package gift.exception.option;

import gift.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OptionsExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundExHandle(NotFoundOptionsException e) {
        return new ErrorResult("상품 옵션 조회 에러", e.getMessage());
    }
}
