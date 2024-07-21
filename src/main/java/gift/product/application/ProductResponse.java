package gift.product.application;

import gift.option.application.OptionResponse;
import gift.product.domain.Product;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        Long categoryId,
        List<OptionResponse> optionResponseList
){
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getOptions().stream()
                        .map(OptionResponse::from)
                        .toList()
        );
    }
}
