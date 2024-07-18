package gift.controller.dto.request;

import gift.service.dto.CreateProductDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record AdminCreateProductRequest(
        @NotBlank
        @Length(max = 15)
        @Pattern(regexp = "^[()\\[\\]+\\-&/_ㄱ-하-ㅣ가-힣a-zA-Z0-9\\s]*$",
                message = "( ), [ ], +, -, &, /, _ 을 제외한 특수 문자는 사용이 불가합니다.")
        String name,
        @Min(-1)
        int price,
        @NotBlank
        String imageUrl,
        @Min(1)
        Long categoryId,

        @NotBlank
        String optionName,
        @Min(1) @Max(99_999_999)
        int optionQuantity
) {
        public CreateProductDto toDto() {
                return new CreateProductDto(name, price, imageUrl, categoryId, optionName, optionQuantity);
        }
}

