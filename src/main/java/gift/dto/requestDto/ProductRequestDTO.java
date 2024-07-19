package gift.dto.requestDto;

import gift.dto.common.valid.ValidProductName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
    @ValidProductName
    String name,
    @Min(1)
    int price,
    @NotBlank
    String imageUrl,
    @NotNull
    Long categoryId
) {

}
