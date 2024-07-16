package gift.product.service.dto;

import gift.product.domain.Category;

public record CategoryParam(
        String name,
        String color,
        String imgUrl,
        String description
) {
    public static Category toEntity(CategoryParam categoryParam) {
        return new Category(
                categoryParam.name(),
                categoryParam.color(),
                categoryParam.imgUrl(),
                categoryParam.description()
        );
    }
}
