package gift.exception;

public class CannotDeleteLastOptionException extends RuntimeException{
    public CannotDeleteLastOptionException (String message){
        super(message);
    }
}
