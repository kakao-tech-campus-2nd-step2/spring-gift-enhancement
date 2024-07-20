package gift.util.mapper;

import gift.product.category.entity.Category;
import gift.product.dto.request.CreateProductRequest;
import gift.product.dto.response.ProductResponse;
import gift.product.entity.Product;

public class ProductMapper {

    public static Product toProduct(CreateProductRequest createProductRequest, Category category) {
        return Product.builder()
            .name(createProductRequest.name())
            .price(createProductRequest.price())
            .imageUrl(createProductRequest.imageUrl())
            .category(category)
            .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategoryName());
    }

}
