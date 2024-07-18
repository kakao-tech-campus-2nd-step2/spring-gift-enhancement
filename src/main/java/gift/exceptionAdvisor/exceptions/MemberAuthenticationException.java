package gift.exceptionAdvisor.exceptions;

import org.springframework.http.HttpStatus;

public class MemberAuthenticationException extends MemberServiceException {

    public MemberAuthenticationException() {
        super("인증 실패", HttpStatus.FORBIDDEN);
    }

    public MemberAuthenticationException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
