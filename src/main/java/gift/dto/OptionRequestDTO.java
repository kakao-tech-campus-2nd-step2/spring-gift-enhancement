package gift.dto;

import jakarta.validation.constraints.*;

public record OptionRequestDTO(
        Long productId,
        String name,
        int quantity
) {
}
