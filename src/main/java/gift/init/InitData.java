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

    @Autowired
    public InitData(UserCreator userCreator, CategoryCreator categoryCreator,
        ProductCreator productCreator, WishCreator wishCreator) {
        this.userCreator = userCreator;
        this.categoryCreator = categoryCreator;
        this.productCreator = productCreator;
        this.wishCreator = wishCreator;
    }

    @PostConstruct
    public void init() {
        userCreator.UserCreator();
        categoryCreator.CategoryCreator();
        productCreator.ProductCreator();
        wishCreator.WishCreator();
    }
}
