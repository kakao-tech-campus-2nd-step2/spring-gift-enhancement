package gift.domain.exception.forbidden;

import gift.domain.exception.ServerException;

public class MemberNotAdminException extends ServerException {

    public MemberNotAdminException() {
        super("어드민 권한이 없습니다.");
    }
}
