package gift.common.exception;

import gift.product.ProductErrorCode;

public class ProductException extends RuntimeException {

    public ProductException(ProductErrorCode productErrorCode) {
        super(productErrorCode.getMessage());
    }

}
