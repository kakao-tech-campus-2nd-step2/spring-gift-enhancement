package gift.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record OptionSubtractRequest(
        @Min(value = 1, message = "수량은 최소 1개 이상, 1억개 미만입니다.")
        @Max(value = 100000000, message = "수량은 최소 1개 이상, 1억개 미만입니다.")
        Integer quantity) {
}
