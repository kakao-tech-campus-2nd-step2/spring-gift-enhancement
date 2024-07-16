package gift.controller.product.dto;

import gift.model.product.Product;
import gift.service.product.dto.ProductModel;

public class ProductResponse {

    public record Info(
        Long productId,
        String name,
        Integer price,
        String imageUrl,
        String category
    ) {

        public static Info from(ProductModel.Info model) {
            return new Info(
                model.id(),
                model.name(),
                model.price(),
                model.imageUrl(),
                model.category()
            );
        }
    }
}
