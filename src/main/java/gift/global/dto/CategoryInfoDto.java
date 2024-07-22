package gift.global.dto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record CategoryInfoDto(
    long categoryId,
    String name,
    String imageUrl
) {

}
