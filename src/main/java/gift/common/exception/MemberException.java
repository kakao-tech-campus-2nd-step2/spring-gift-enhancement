package gift.common.exception;

import gift.member.MemberErrorCode;
import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {

    private final MemberErrorCode memberErrorCode;
    private final HttpStatus httpStatus;
    private final String detailMessage;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
        this.httpStatus = memberErrorCode.getHttpStatus();
        this.detailMessage = memberErrorCode.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
