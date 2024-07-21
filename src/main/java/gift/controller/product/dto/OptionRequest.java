package gift.controller.product.dto;

import gift.service.product.dto.OptionCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OptionRequest {

    public record Register(
        @NotNull
        List<Info> options
    ) {

        public OptionCommand.RegisterMany toCommand() {
            return new OptionCommand.RegisterMany(options.stream()
                .map(Info::toCommand)
                .toList());
        }
    }

    public record Update(
        @NotBlank
        String name,
        @Min(0)
        Integer quantity
    ) {

        public OptionCommand.Update toCommand() {
            return new OptionCommand.Update(name, quantity);
        }

    }

    public record Info(
        @NotBlank
        String name,
        @Min(0)
        Integer quantity
    ) {

        public OptionCommand.Info toCommand() {
            return new OptionCommand.Info(name, quantity);
        }
    }

}
