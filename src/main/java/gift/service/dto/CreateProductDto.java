package gift.service.dto;

public record CreateProductDto(
        String name,
        int price,
        String imageUrl,
        Long categoryId,
        String optionName,
        int optionQuantity
) {
}

