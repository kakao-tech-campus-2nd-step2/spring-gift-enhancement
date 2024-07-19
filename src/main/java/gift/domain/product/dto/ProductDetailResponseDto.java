package gift.domain.product.dto;

import gift.domain.product.entity.Product;
import java.util.List;

public record ProductDetailResponseDto(
    Long id,
    CategoryResponseDto category,
    String name,
    int price,
    String imageUrl,
    List<OptionDto> options
) {
    public static ProductDetailResponseDto from(Product product) {
        return new ProductDetailResponseDto(
            product.getId(),
            CategoryResponseDto.from(product.getCategory()),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getOptions().stream().map(OptionDto::from).toList()
        );
    }
}
