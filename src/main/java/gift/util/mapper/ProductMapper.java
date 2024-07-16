package gift.util.mapper;

import gift.dto.product.request.CreateProductRequest;
import gift.dto.product.request.UpdateProductRequest;
import gift.dto.product.response.ProductResponse;
import gift.entity.Category;
import gift.entity.Product;

public class ProductMapper {

    public static Product toProduct(CreateProductRequest createProductRequest, Category category) {
        return Product.builder()
            .name(createProductRequest.name())
            .price(createProductRequest.price())
            .imageUrl(createProductRequest.imageUrl())
            .category(category)
            .build();
    }

    public static void updateProduct(Product product, UpdateProductRequest request) {
        product.changeName(request.name());
        product.changePrice(request.price());
        product.changeImageUrl(request.imageUrl());
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategoryName());
    }

}
