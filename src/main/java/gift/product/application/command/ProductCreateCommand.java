package gift.product.application.command;

import gift.category.domain.Category;
import gift.product.domain.Product;

public record ProductCreateCommand(
        String name,
        Integer price,
        String imageUrl,
        Long categoryId
) {
    public Product toProduct(Category category) {
        return new Product(name, price, imageUrl, category);
    }
}
