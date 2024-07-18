package gift.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OptionRequest {

    public record Create(
            @NotBlank
            @Pattern(regexp = "^[()\\[\\]+\\-&/_ㄱ-하-ㅣ가-힣a-zA-Z0-9\\s]*$",
                    message = "( ), [ ], +, -, &, /, _ 을 제외한 특수 문자는 사용이 불가합니다.")
            String name,
            @Min(1) @Max(100_000_000)
            int quantity,
            @Min(1)
            Long productId
    ) {
    }

    public record Update(
            @Min(1)
            Long id,
            @NotBlank
            @Pattern(regexp = "^[()\\[\\]+\\-&/_ㄱ-하-ㅣ가-힣a-zA-Z0-9\\s]*$",
                    message = "( ), [ ], +, -, &, /, _ 을 제외한 특수 문자는 사용이 불가합니다.")
            String name,
            @Min(1) @Max(100_000_000)
            int quantity,
            @Min(1)
            Long productId
    ) {
    }
}
