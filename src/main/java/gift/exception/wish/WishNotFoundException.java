package gift.exception.wish;

import gift.exception.NotFoundException;

public class WishNotFoundException extends NotFoundException {

    public WishNotFoundException(String message) {
        super(message);
    }

}
