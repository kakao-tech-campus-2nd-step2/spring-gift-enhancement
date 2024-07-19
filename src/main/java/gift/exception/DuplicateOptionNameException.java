package gift.exception;

public class DuplicateOptionNameException extends RuntimeException {

    public DuplicateOptionNameException() {
        super("이미 등록된 이름의 옵션입니다.");
    }
}
