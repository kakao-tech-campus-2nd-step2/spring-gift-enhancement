package gift.util.mapper;

import gift.dto.product.CreateProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;

public class ProductMapper {

    public static Product toProduct(CreateProductRequest createProductRequest) {
        return Product.builder()
            .name(createProductRequest.name())
            .price(createProductRequest.price())
            .imageUrl(createProductRequest.imageUrl())
            .build();
    }

    public static void updateProduct(Product product, UpdateProductRequest request) {
        product.changeName(request.name());
        product.changePrice(request.price());
        product.changeImageUrl(request.imageUrl());
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }

}
