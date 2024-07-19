package gift.common.exception;

import gift.product.ProductErrorCode;
import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException {

    private final ProductErrorCode productErrorCode;
    private final HttpStatus httpStatus;
    private final String detailedMessage;

    public ProductException(ProductErrorCode productErrorCode) {
        super(productErrorCode.getMessage());
        this.productErrorCode = productErrorCode;
        this.httpStatus = productErrorCode.getHttpStatus();
        this.detailedMessage = productErrorCode.getMessage();
    }

}
