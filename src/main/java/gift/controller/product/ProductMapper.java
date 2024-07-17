package gift.controller.product;

import gift.domain.Product;

public class ProductMapper {

    public static Product from(ProductRequest productRequest) {
        return new Product(productRequest.name(), productRequest.price(),
            productRequest.imageUrl());
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }
}