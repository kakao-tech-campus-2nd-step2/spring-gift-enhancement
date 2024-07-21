package gift.controller.dto.request;

import jakarta.validation.constraints.Min;

public class WishRequest {

    public record Create(
            @Min(1)
            Long productId
    ) {
    }

    public record Update(
            @Min(1)
            Long id,
            @Min(1)
            Long productId,
            @Min(0)
            int productCount
    ) {
    }

}
