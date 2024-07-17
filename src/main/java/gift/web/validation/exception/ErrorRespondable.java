package gift.web.validation.exception;

import gift.web.validation.exception.code.ErrorCode;

public interface ErrorRespondable {

    /**
     * 예외가 어떤 {@link ErrorCode} 를 가지는지 반환한다.
     * @return {@link ErrorCode}
     */
    ErrorCode getErrorCode();

    String getMessage();

}
