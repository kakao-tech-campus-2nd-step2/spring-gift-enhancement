package gift.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitData {

    private final UserCreator userCreator;
    private final CategoryCreator categoryCreator;
    private final ProductCreator productCreator;
    private final WishCreator wishCreator;
    private final ProductOptionCreator productOptionCreator;
    private final ProductOrderCreator productOrderCreator;

    @Autowired
    public InitData(UserCreator userCreator, CategoryCreator categoryCreator,
        ProductCreator productCreator, WishCreator wishCreator,
        ProductOptionCreator productOptionCreator, ProductOrderCreator productOrderCreator) {
        this.userCreator = userCreator;
        this.categoryCreator = categoryCreator;
        this.productCreator = productCreator;
        this.wishCreator = wishCreator;
        this.productOptionCreator = productOptionCreator;
        this.productOrderCreator = productOrderCreator;
    }

    @PostConstruct
    public void init() {
//        유저 생성
        userCreator.UserCreator();
//        카테고리 생성
        categoryCreator.CategoryCreator();
//        상품 생성
        productCreator.ProductCreator();
//        위시리스트 생성
        wishCreator.WishCreator();
//        상품의 옵션 추가
        productOptionCreator.ProductOptionCreator();
//        상품의 옵션 재고 증감(상품주문)
        productOrderCreator.ProductCreator();
    }
}
