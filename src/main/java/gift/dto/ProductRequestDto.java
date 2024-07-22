package gift.dto;

import gift.validation.NameValidator;
import gift.validation.ValidName;
import gift.vo.Category;
import gift.vo.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ProductRequestDto(
        Long id,

        Long categoryId,

        @ValidName
        @NotEmpty(message = "상품명을 입력해 주세요.")
        String name,

        @Positive(message = "가격을 입력해 주세요(0보다 커야 합니다.)")
        int price,

        String imageUrl,

        List<@Valid OptionRequestDto> options
) {
    public Product toProduct(Category category) {
        validateProductName();
        return new Product(id, category, name, price, imageUrl);
    }

    private void validateProductName() {
        String nameKakaoErrorMessage = NameValidator.isValidKakaoName(name);
        if(nameKakaoErrorMessage != null) {
            throw new IllegalArgumentException(nameKakaoErrorMessage);
        }
    }
}
