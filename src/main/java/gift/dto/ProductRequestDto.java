package gift.dto;

import gift.validation.NameValidator;
import gift.validation.ValidName;
import gift.vo.Category;
import gift.vo.Product;
import jakarta.validation.constraints.*;

public record ProductRequestDto(
        Long id,

        Long categoryId,

        @ValidName
        @NotEmpty(message = "상품명을 입력해 주세요.")
        String name,

        @Positive(message = "가격을 입력해 주세요(0보다 커야 합니다.)")
        int price,

        String imageUrl
) {
    public Product toProduct(Category category) {
        String nameKakaoErrorMessage = NameValidator.isValidKakaoName(name);
        if(nameKakaoErrorMessage != null) {
            throw new IllegalArgumentException(nameKakaoErrorMessage);
        }
        return new Product(id, category, name, price, imageUrl);
    }
}
