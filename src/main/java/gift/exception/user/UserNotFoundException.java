package gift.exception.user;


import gift.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
