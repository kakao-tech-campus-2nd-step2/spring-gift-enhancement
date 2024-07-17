package gift.product.service.dto;

import gift.product.domain.Category;
import gift.product.domain.Product;

public record ProductParam(
        String name,
        Integer price,
        String imgUrl,
        String categoryName
) {
    public Product toEntity(Category category) {
        return new Product(name, price, imgUrl, category);
    }
}
