package gift.exception.product;

import gift.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
