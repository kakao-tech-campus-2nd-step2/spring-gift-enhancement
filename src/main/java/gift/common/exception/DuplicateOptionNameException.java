package gift.common.exception;

public class DuplicateOptionNameException extends RuntimeException{

    public DuplicateOptionNameException() {
        super("중복된 옵션 이름입니다.");
    }
}
