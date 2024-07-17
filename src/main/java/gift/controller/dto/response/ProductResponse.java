package gift.controller.dto.response;

import gift.model.Option;
import gift.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(Long id, String name, int price,
                              String imageUrl, String category,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), product.getCategory().getName(),
                product.getCreatedAt(), product.getUpdatedAt());
    }
}
