package gift.dto;

import jakarta.validation.constraints.*;

public record OptionRequestDTO(
        Long productId,
        @NotBlank
        @Size(max = 50)
        @Pattern(regexp = "^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/]*$", message = "옵션 이름에는 허용되지 않은 특수 문자가 포함되어 있습니다")
        String name,
        @Min(1)
        @Max(99999999)
        int quantity
) {
}
