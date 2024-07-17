package gift.controller.dto.response;

import gift.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public record ProductWithOptionResponse(Long id, String name, int price,
                                       String imageUrl, CategoryResponse category,
                                       List<OptionResponse> options,
                                       LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ProductWithOptionResponse from(Product product) {
        return new ProductWithOptionResponse(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), CategoryResponse.from(product.getCategory()),
                product.getOptions().stream().map(OptionResponse::from).toList(),
                product.getCreatedAt(), product.getUpdatedAt());
    }
}
