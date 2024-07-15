package gift.exception.wish;

import gift.exception.NotFoundException;

public class WishNotFoundException extends NotFoundException {

    public WishNotFoundException() {
        this("위시리스트를 찾을 수 없습니다.");
    }


    public WishNotFoundException(String message) {
        super(message);
    }


}
