package gift.controller.product.dto;


import gift.model.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    public record Register(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl,
        @NotNull
        Long categoryId
    ) {

    }

    public record Update(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl
    ) {

    }
}
