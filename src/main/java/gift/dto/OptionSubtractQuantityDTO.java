package gift.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OptionSubtractQuantityDTO(
    @NotNull(message = "차감할 수량을 입력해야 합니다.")
    @Min(value = 1, message = "차감할 수량은 0보다 커야 합니다.")
    Long subtractQuantity
) {

}
