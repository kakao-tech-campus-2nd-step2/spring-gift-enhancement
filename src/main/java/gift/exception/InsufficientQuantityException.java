package gift.exception;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException() {
        super("해당 옵션의 수량이 부족합니다.");
    }
}
