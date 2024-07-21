package gift.domain.validator;

import gift.domain.ProductOption;
import java.util.List;

public abstract class ProductValidator {

    public static void validateProductOptionsPresence(List<ProductOption> productOptions) {
        if (productOptions == null || productOptions.isEmpty()) {
            throw new IllegalArgumentException("상품 옵션은 최소 1개 이상이어야 합니다.");
        }
    }

}
