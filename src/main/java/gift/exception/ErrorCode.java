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
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");


    private HttpStatusCode status;
    private String message;

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
