package gift.exception.product;

import gift.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException() {
        this("해당 상품을 찾을 수 없습니다.");
    }


    public ProductNotFoundException(String message) {
        super(message);
    }


}
