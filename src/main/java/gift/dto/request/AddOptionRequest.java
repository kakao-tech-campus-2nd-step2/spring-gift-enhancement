package gift.dto.request;

import jakarta.validation.constraints.*;

import static gift.constant.Message.*;

public record AddOptionRequest(
        @NotBlank(message = REQUIRED_FIELD_MSG)
        @Size(max = 50, message = LENGTH_ERROR_MSG)
        @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = SPECIAL_CHAR_ERROR_MSG)
        String name,

        @NotNull(message = REQUIRED_FIELD_MSG)
        @Min(1) @Max(100_000_000)
        Integer quantity) {
}
