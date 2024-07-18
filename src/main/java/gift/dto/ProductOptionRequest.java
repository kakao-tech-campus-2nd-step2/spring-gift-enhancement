package gift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductOptionRequest(
        @NotNull(message = "상품은 반드시 선택되어야 합니다.")
        Long productId,
        @NotBlank(message = "이름의 길이는 최소 1자 이상이어야 합니다.")
        String name,
        @PositiveOrZero(message = "추가 금액은 0보다 크거나 같아야 합니다.")
        Integer additionalPrice) {
}
