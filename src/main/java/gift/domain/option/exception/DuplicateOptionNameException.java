package gift.domain.option.exception;

public class DuplicateOptionNameException extends RuntimeException{
    public DuplicateOptionNameException(String message){
        super(message);
    }
}
