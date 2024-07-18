package gift.exceptionAdvisor.exceptions;

import org.springframework.http.HttpStatus;

public class MemberAuthenticationException extends MemberServiceException {

    public MemberAuthenticationException() {
        super("옳지 못한 인증 시도 입니다.", HttpStatus.FORBIDDEN);
    }

    public MemberAuthenticationException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
