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
        userCreator.UserCreator();
        categoryCreator.CategoryCreator();
        productCreator.ProductCreator();
        wishCreator.WishCreator();
        productOptionCreator.ProductOptionCreator();
        productOrderCreator.ProductCreator();
    }
}
