package gift.domain.validator;

public abstract class ProductOptionValidator {

    public static void validatedRequestQuantity(int stock, int quantity) {
        if(quantity > stock) {
            throw new IllegalStateException("재고보다 많은 수량을 요청할 수 없습니다.");
        }
    }

}
