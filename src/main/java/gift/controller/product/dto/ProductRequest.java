package gift.controller.product.dto;


import gift.controller.product.dto.OptionRequest.Register;
import gift.service.product.dto.ProductCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

    public record Register(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl,
        @NotNull
        Long categoryId,
        @NotNull
        OptionRequest.Register options
    ) {

        public ProductCommand.Register toCommand() {
            return new ProductCommand.Register(name, price, imageUrl, categoryId);
        }


    }

    public record Update(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl,
        @NotNull
        Long categoryId
    ) {

        public ProductCommand.Update toCommand() {
            return new ProductCommand.Update(name, price, imageUrl, categoryId);
        }
    }
}
