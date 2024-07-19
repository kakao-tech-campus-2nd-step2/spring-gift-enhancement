package gift.product.presentation.dto;

import gift.product.business.dto.ProductUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record RequestProductUpdateDto(
    @NotBlank
    @Size(max = 15)
    @Pattern(
        regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣 ()\\[\\]+\\-&/_]*$",
        message = "오직 문자, 공백 그리고 특수문자 (),[],+,&,-,/,_만 허용됩니다."
    )
    @Pattern(
        regexp = "(?!.*카카오).*",
        message = "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."
    )
    String name,
    @NotNull @Min(0) Integer price,
    @Size(max = 255) String description,
    @URL String imageUrl,
    @NotNull Long categoryId
) {

    public ProductUpdateDto toProductUpdateDto() {
        return new ProductUpdateDto(name, price, description, imageUrl, categoryId);
    }
}
