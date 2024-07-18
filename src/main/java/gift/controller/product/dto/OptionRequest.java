package gift.controller.product.dto;

import gift.service.product.dto.OptionCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OptionRequest {

    public record Register(
        @NotBlank
        String name,
        @NotNull
        Integer quantity
    ) {

        public OptionCommand.Register toCommand() {
            return new OptionCommand.Register(name, quantity);
        }
    }

    public record Update(
        @NotBlank
        String name,
        @NotNull
        Integer quantity
    ) {

        public OptionCommand.Update toCommand() {
            return new OptionCommand.Update(name, quantity);
        }

    }

}
