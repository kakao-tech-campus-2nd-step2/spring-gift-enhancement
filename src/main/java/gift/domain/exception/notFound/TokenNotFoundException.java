package gift.domain.exception.notFound;

public class TokenNotFoundException extends NotFoundException {

    public TokenNotFoundException() {
        super("토큰이 존재하지 않습니다. 발급이 필요합니다.");
    }
}
