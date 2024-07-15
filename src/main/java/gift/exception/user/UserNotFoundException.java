package gift.exception.user;


import gift.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        this("사용자를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }


}
