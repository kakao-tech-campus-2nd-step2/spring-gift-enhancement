package gift.dto.requestDTO;

import gift.domain.Product;
import gift.dto.common.valid.ValidProductName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
    @ValidProductName
    String name,
    @Min(1)
    int price,
    @NotBlank
    String imageUrl) {

    public static Product toEntity(ProductRequestDTO productRequestDTO) {
        return new Product(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl());
    }
}
