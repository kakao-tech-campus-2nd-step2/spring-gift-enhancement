package gift.exceptionAdvisor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class MemberServiceException extends GiftException {

    public MemberServiceException(String message, HttpStatus responseStatus) {
        super(message, responseStatus);
    }
}
