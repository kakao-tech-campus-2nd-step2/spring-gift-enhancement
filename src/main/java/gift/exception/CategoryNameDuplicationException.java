package gift.exception;

public class CategoryNameDuplicationException extends RuntimeException{

    private static final String MESSAGE = "중복된 카테고리 이름 입니다.";

    public CategoryNameDuplicationException(){
        super(MESSAGE);
    }


    public CategoryNameDuplicationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
