package gift.product.dto;

import gift.category.dto.CategoryResponseDto;
import gift.product.entity.Product;
import java.util.List;

public record ProductResponseDto(
    long productId,
    String name,
    int price,
    String imageUrl,
    CategoryResponseDto category,
    List<OptionExceptProductResponseDto> options
) {

    public static ProductResponseDto fromProduct(Product product) {
        var categoryResponseDto = CategoryResponseDto.fromCategory(product.getCategory());
        var optionResponseDtoList = product.getOptions().toList().stream()
            .map(OptionExceptProductResponseDto::fromOption).toList();

        return new ProductResponseDto(product.getProductId(), product.getName(), product.getPrice(),
            product.getImageUrl(), categoryResponseDto, optionResponseDtoList);
    }
}