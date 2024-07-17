package gift.exceptionAdvisor;

import org.springframework.http.HttpStatus;

public class CategoryServiceException extends RuntimeException{

     private HttpStatus responseStatus;

    public CategoryServiceException() {
    }

    public CategoryServiceException(String message, HttpStatus responseStatus) {
         super(message);
         this.responseStatus = responseStatus;
     }
}
