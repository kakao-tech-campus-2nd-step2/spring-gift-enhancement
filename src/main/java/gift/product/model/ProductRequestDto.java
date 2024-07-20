package gift.product.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
    @NotBlank
    @Size(min = 1, max = 15, message = "상품 이름은 공백을 포함하여 최대 15자입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = "사용가능한 특수 문자는 (),[],+,-,&,/,_ 입니다.")
    String name,
    @NotNull
    @Min(value = 0, message = "가격은 0원 이상입니다.")
    Integer price,
    @NotBlank
    String imageUrl,
    @NotNull
    Long categoryId
) {

    public static ProductRequestDto from(ProductResponseDto productResponseDto) {
        return new ProductRequestDto(
            productResponseDto.name(),
            productResponseDto.price(),
            productResponseDto.imageUrl(),
            productResponseDto.categoryId()
        );
    }
}
