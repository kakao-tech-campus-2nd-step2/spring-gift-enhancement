package gift.product.business.dto;

import gift.product.persistence.entity.Category;
import gift.product.persistence.entity.Product;

public record ProductRegisterDto(
    String name,
    String description,
    Integer price,
    String url,
    Long categoryId
) {

    public Product toProduct(Category category) {
        return new Product(name, description, price, url, category);
    }
}
