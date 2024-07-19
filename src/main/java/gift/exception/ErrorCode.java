package gift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "위시리스트를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXIST(HttpStatus.CONFLICT, "상품이 이미 존재합니다."),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "사용자가 이미 존재합니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    OPTION_NAME_DUPLICATE(HttpStatus.CONFLICT, "중복된 이름의 옵션이 존재합니다."),
    INVALID_OPTION_QUANTITY(HttpStatus.BAD_REQUEST, "옵션 수량은 1개 이상 1억 개 미만입니다."),
    INVALID_OPTION_NAME(HttpStatus.BAD_REQUEST,
        "옵션 이름은 최대 50자까지 가능하고, 특수 문자는 `( ), [ ], +, -, &, /, _`만 혀용합니다.");


    private final HttpStatusCode status;
    private final String message;

    ErrorCode(HttpStatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
