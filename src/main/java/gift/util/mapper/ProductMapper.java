package gift.util.mapper;

import gift.product.dto.request.CreateProductRequest;
import gift.product.dto.request.UpdateProductRequest;
import gift.product.dto.response.ProductResponse;
import gift.product.entity.Category;
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

    public static void updateProduct(Product product, UpdateProductRequest request,
        Category category) {
        product.changeName(request.name());
        product.changePrice(request.price());
        product.changeImageUrl(request.imageUrl());
        product.changeCategory(category);
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategoryName());
    }

}
