package gift.exception.customException;

import static gift.exception.exceptionMessage.ExceptionMessage.CATEGORY_NAME_DUPLICATION;

public class CategoryNameDuplicationException extends RuntimeException{


    public CategoryNameDuplicationException(){
        super(CATEGORY_NAME_DUPLICATION);
    }

    public CategoryNameDuplicationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
