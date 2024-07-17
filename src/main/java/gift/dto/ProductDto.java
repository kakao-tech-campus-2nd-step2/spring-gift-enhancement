package gift.dto;

import gift.model.product.Category;
import jakarta.validation.constraints.NotEmpty;

public record ProductDto(Category category,
                      @NotEmpty(message = "상품 이름은 필수 입력값입니다.")
                      String name,
                         int price,
                         String imageUrl,
                         int amount) { }
