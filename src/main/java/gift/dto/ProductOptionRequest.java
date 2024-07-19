package gift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductOptionRequest(
        @NotBlank(message = "이름의 길이는 최소 1자 이상이어야 합니다.")
        String name,
        @Size(min = 1, max = 100000000, message = "수량은 최소 1개 이상, 1억개 미만입니다.")
        Integer quantity) {
}
